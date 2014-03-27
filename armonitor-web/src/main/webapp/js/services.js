'use strict';

/* Services */
services.factory('MessageService', ['$resource','config', function($resource, config) {
	return $resource(config.server + '/msg', {}, {
		trashItem: {
			method: 'GET',
			url: config.server + '/msg/trash/:id',
			params: {id: '@id'},
			isArray: false
		},
		trash: {
			method: 'GET',
			url: config.server + '/msg/trash',
			isArray: false
		},
		close: {
			method: 'GET',
			url: config.server + '/msg/close',
			isArray: false
		},
		info: {
			method: 'GET',
			url: config.server + '/msg/info',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/msg',
			isArray: true
		}
	});
}]);
services.factory('UserService', [function() {
	var _user = null;
	var _roles = null;

	return {
		clear: function() {
			_user = null;
			_roles = null;
		},		
		user: function() {
			return _user;
		},
		setUser: function(user) {
			_user = user;
		},		
		roles: function() {
			return _roles;
		},
		setRoles: function(roles) {
			_roles = roles;
		}		
	};
}]);

services.factory('CommonService', ['$location','UserService','SecurityRSService','MessageService','ErrorService',
	function($location, UserService, SecurityRSService, MessageService, ErrorService) {

	var _base_roles = ["admin", "base"];

	MessageService.info({}, function(response) {
		ErrorService.addInfo(response);
	});

	SecurityRSService.user(function(response) {
		if (response.guid) {
			UserService.setUser(response);
			_load_roles();
		}
	});

	function _load_roles() {
		SecurityRSService.roles({}, _base_roles, function(response) {
			UserService.setRoles(response);			
		});
	}


	return {
		closeMsg: function() {
			MessageService.close({}, function(response) {
				ErrorService.addInfo(response);
			});
		},
		update: function(data, callback) {
			SecurityRSService.save({}, data, function(response) {
				if (response.guid) {
					UserService.setUser(response);
					if (callback) {
						callback(UserService.user());
					}
				}
			});
		},
		login: function(data, callback, error) {
			SecurityRSService.login({}, data, function(response) {
				if (response.guid) {
					UserService.setUser(response);
					_load_roles();
					if (callback) {
						callback(UserService.user());
					}
				}
			}, function(response) {
				if (error) {
					error(response);
				}
			});
		},
		logout: function() {
			SecurityRSService.logout(function(response) {
				$location.url('/');
				UserService.clear();
			});
		},
		user: function() {
			return UserService.user();;
		},
		roles: function() {
			return UserService.roles();;
		},
		check: function(item) {
			var found = false, name;
			if (item) {
				for (name in item) {
					if (item.hasOwnProperty(name)) {
						found = true;
						break;
					}
				}
			}
			return found;
		}
	};
}]);
services.factory('SecurityRSService', ['$resource','config', 
	function($resource, config) {
	return $resource(config.server + '/sec', {}, {
		pswd: {
			method: 'POST',
			url: config.server + '/sec/pr/password',
			isArray: false
		},
		user: {
			method: 'GET',
			url: config.server + '/sec/pr',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/sec/pr',
			isArray: false
		},
		logout: {
			url: config.server + '/sec/pr/logout',
			method: 'GET',
			isArray: false
		},
		roles: {
			method: 'POST',
			url: config.server + '/sec/pr/roles',
			isArray: false
		},
		login: {
			method: 'POST',
			url: config.server + '/sec/login',
			isArray: false
		}
	});
}]);

services.factory('VersionBuildRSService', ['$resource','config', 
	function($resource, config) {
	return $resource(config.server + '/dvb', {}, {
		reload: {
			method: 'GET',
			url: config.server + '/dvb/:app/:ver/reload',
			params: {app: '@app', ver: '@ver'},
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/dvb/:app/:ver',
			params: {app: '@app', ver: '@ver'},
			isArray: false
		}
	});
}]);

services.factory('ApplicationBuildRSService', ['$resource','config', 
	function($resource, config) {
	return $resource(config.server + '/dab', {}, {
		reload: {
			method: 'GET',
			url: config.server + '/dab/:guid/reload',
			params: {guid: '@guid'},
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/dab/:guid',
			params: {guid: '@guid'},
			isArray: false
		}
	});
}]);

services.factory('SystemBuildRSService', ['$resource','config', 
	function($resource, config) {
	return $resource(config.server + '/dsb', {}, {
		reload: {
			method: 'GET',
			url: config.server + '/dsb/:guid/reload',
			params: {guid: '@guid'},
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/dsb/:guid',
			params: {guid: '@guid'},
			isArray: false
		}
	});
}]);

services.factory('ActivityRSService', ['$resource','config', 
	function($resource, config) {
	return $resource(config.server + '/ac', {}, {
		reload: {
			method: 'GET',
			url: config.server + '/ac/build/:guid/reload',
			params: {guid: '@guid'},
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ac/build/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		reloadNow: {
			method: 'GET',
			url: config.server + '/ac/build/:guid/now/reload',
			params: {guid: '@guid'},
			isArray: false
		},
		now: {
			method: 'GET',
			url: config.server + '/ac/build/:guid/now',
			params: {guid: '@guid'},
			isArray: false
		}		
	});
}]);

services.factory('BuildRSService', ['$resource','config', 
	function($resource, config) {
	return $resource(config.server + '/build', {}, {
		search: {
			method: 'POST',
			isArray: true
		},
		get: {
			method: 'GET',
			url: config.server + '/build/:guid',
			params: {guid: '@guid'},
			isArray: false
		}
	});
}]);

services.factory('ErrorService', [function() {
	var _data = null;
	var _info = null;
	
	return {
		info: function() {
			return _info;
		},
		addInfo: function(data) {
			_info = data;
		},
		error: function() {	
			return _data;
		},
		close: function() {
			_data = null;
		},		
		addError: function(item) {
			_data = item;
		}
	};
}]);

services.factory('DashboardRSService', ['$resource','config', 
	function($resource, config) {
	return $resource(config.server + '/db', {}, {
		get: {
			method: 'GET',
			isArray: false
		},
		msg: {
			method: 'GET',
			url: config.server + '/db/msg',
			isArray: false
		},
		reload: {
			method: 'GET',
			url: config.server + '/db/reload',
			isArray: false
		},
		updateBuild: {
			method: 'GET',
			url: config.server + '/db/sys/:sys/build',
			params: {sys: '@sys'},
			isArray: false
		}
	});
}]);