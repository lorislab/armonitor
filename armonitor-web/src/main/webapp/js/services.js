'use strict';

/* Services */
angular.module('armonitor.services', ['ngResource'])
		.factory('BuildCriteriaService', function() {
			var app = null;
			var build = null;
			
			return  {
				app: function() {
					return app;
				},
				build: function() {
					return build;
				},
				criteria: function() {
					if (build) {
						return {application: app.guid, params: false, mavenVersion: build.mavenVersion}
					}	
					return {application: app.guid, params: false, mavenVersion: null}
				},
				set: function(a, b) {
					app = a;
					build = b;
				}
			};
		})
		.factory('BuildRSService', function($resource, config) {
			return $resource(config.server + '/build', {}, {
				search: {
					method: 'POST',
					isArray: true
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
		})	
		.value('version', '0.1');