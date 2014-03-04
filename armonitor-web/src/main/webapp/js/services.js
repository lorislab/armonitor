'use strict';

/* Services */
angular.module('armonitor.services', ['ngResource'])
		.factory('CommonService', function() {
			return {
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