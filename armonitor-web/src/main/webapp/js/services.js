'use strict';

/* Services */
angular.module('armonitor.services', ['ngResource'])
		.factory('UserAdminService', function($resource, config) {
			return $resource(config.server + '/user', {}, {
				save: {
					method: 'POST',
					url: config.server + '/user',
					isArray: false
				},
				pswd: {
					method: 'POST',
					url: config.server + '/user/:guid/password',
					params: {guid: '@guid'},
					isArray: false
				}				
			});
		})
		.factory('CommonService', function(SecurityRSService, $location) {

			var _base_roles = ["admin", "base"];
			var _user = null;
			var _roles = null;

			function _startup() {
				SecurityRSService.get(function(response) {
					if (response.guid) {
						_user = response;
						_load_roles();
					}
				});
			}

			function _load_roles() {
				SecurityRSService.roles({}, _base_roles, function(response) {
					_roles = response;
				});
			}

			_startup();

			return {
				update: function(data, callback) {
					SecurityRSService.save({}, data, function(response) {
						if (response.guid) {
							_user = response;
							if (callback) {
								callback(_user);
							}
						}
					});
				},
				login: function(data, callback) {
					SecurityRSService.login({}, data, function(response) {
						if (response.guid) {
							_user = response;
							_load_roles();
							if (callback) {
								callback(_user);
							}
						}
					}, function(response) {
						if (callback) {
							callback(_user);
						}
					});
				},
				logout: function() {
					SecurityRSService.logout(function(response) {
						$location.url('/');
						_user = null;
						_roles = null;
					});
				},
				user: function() {
					return _user;
				},
				roles: function() {
					return _roles;
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
		})
		.factory('SecurityRSService', function($resource, config) {
			return $resource(config.server + '/sec', {}, {
				pswd: {
					method: 'POST',
					url: config.server + '/password',
					isArray: false
				},				
				user: {
					method: 'GET',
					url: config.server + '/sec',
					isArray: false
				},
				save: {
					method: 'POST',
					url: config.server + '/sec',
					isArray: false
				},
				logout: {
					url: config.server + '/sec/logout',
					method: 'GET',
					isArray: false
				},
				roles: {
					method: 'POST',
					url: config.server + '/sec/roles',
					isArray: false
				},
				login: {
					method: 'POST',
					url: config.server + '/sec/login',
					isArray: false
				}
			});
		})
		.factory('VersionBuildRSService', function($resource, config) {
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
		})
		.factory('ApplicationBuildRSService', function($resource, config) {
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
		})
		.factory('SystemBuildRSService', function($resource, config) {
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
		})
		.factory('ActivityRSService', function($resource, config) {
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
				}
			});
		})
		.factory('BuildRSService', function($resource, config) {
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
		})
		.factory('DashboardRSService', function($resource, config) {
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
		});