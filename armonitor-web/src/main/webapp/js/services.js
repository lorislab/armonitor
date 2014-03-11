'use strict';

/* Services */
angular.module('armonitor.services', ['ngResource'])
		.factory('CommonService', function($rootScope, LoginRSService) {
			
			var _user = null;
	
			function _startup() {
				LoginRSService.get(function(response) {
					if (response.guid) {
						_user = response;		
					}
				});
			}
			
			_startup();
			
			return {
				login: function(user) {
					_user = user;
				},
				logout: function() {
					LoginRSService.logout(function(response) {
						_user = null;
					});
				},
				user: function() {
					return _user;
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
//		.factory('LoginService2', function($http, config) {
//			var _config = {
//				headers: {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}
//			};
//			return {
//				login: function(username, password, listener) {					
//					$http.post('j_security_check', {j_username: username, j_password: password}, _config).success(function(data) {
//						var response = (data === 'AUTHENTICATION_SUCCESS');
//						if (listener) {
//							listener(response);
//						}
//					});
//				}
//			};
//		})		
		.factory('LoginRSService', function($resource, config) {
			return $resource(config.server + '/sec', {}, {
				user: {
					method: 'GET',
					url: config.server + '/sec',					
					isArray: false					
				},
				logout: {
					url: config.server + '/sec/logout',
					method: 'GET',
					isArray: false
				},
				login: {
					method: 'POST',
					url: config.server + '/sec',
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