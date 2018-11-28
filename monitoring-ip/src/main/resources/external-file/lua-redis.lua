-- First add the above in "server{}"
-- lua_shared_dict ip_blacklist 1m;
-- lua_package_path "/usr/example/lualib/?.lua;;";
-- lua_package_cpath "/usr/example/lualib/?.so;;";
-- First add the above in "server{}"

-- ngx.log(ngx.DEBUG, "--------ip black list start--------")
-- rewrite_by_lua_file|access_by_lua_file|content_by_lua_file ../lua-redis.lua;
if ngx.var.uri == '/api/message/sms-send' then
	
	--  time interval (unit:s)
	local cache_ttl = 300

	local ip = ngx.var.remote_addr-- ip
	local ip_blacklist = ngx.shared.ip_blacklist-- shared memory:lua_shared_dict ip_blacklist 1m;
	local last_update_time = ip_blacklist:get("last_update_time")-- Last update time

	-- Update every time period
	if last_update_time == nil or last_update_time < ( ngx.now() - cache_ttl) then
		
		-- redis time out, don't set it too high
		local redis_connection_timeout = 100
		-- Redis ip black list
		local redis_key = "ip_list:ip:ip_black_list"
		-- host
		local host = "118.178.224.151"
		-- redis port
		local port = 6379
		

		local redis = require "resty.redis";
		local red = redis:new();

		red:set_timeout(redis_connection_timeout);-- Time out

		local ok, err = red:connect(host, port);
		ok, err = red:auth("zk86715658")-- password
		if not ok then
			ngx.log(ngx.DEBUG, "error:" .. err);
		else
			local new_ip_blacklist, err = red:smembers(redis_key);
			if err then
				ngx.log(ngx.DEBUG, "error:" .. err)
			else
				-- flush all
				ip_blacklist:flush_all();
				-- add new black list
				for index, banned_ip in ipairs(new_ip_blacklist) do
					ip_blacklist:set(banned_ip,true);
				end

				-- update time
				ip_blacklist:set("last_update_time", ngx.now());

				-- flush expired max count 100
				ip_blacklist:flush_expired(100);
			end

		end
	end

	if ip_blacklist:get(ip) then
		ngx.log(ngx.DEBUG, "ip black list:" .. ip)
		return ngx.exit(ngx.HTTP_FORBIDDEN);
	end
	-- ngx.say("succeed!")
	-- ngx.log(ngx.DEBUG, "--------ip black list end--------")
end
