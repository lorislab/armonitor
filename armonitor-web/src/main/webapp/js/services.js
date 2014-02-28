'use strict';

/* Services */
angular.module('armonitor.services', ['ngResource'])	
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
				getApp: {
					method: 'GET',
					url: config.server + '/db/app',
					isArray: false
				},				
				getDashboardBuilds: {
					method: 'GET',
					url: config.server + '/db/builds',
					isArray: true
				},
				setDashboardBuilds: {
					method: 'POST',
					url: config.server + '/db/builds',
					isArray: false
				},				
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