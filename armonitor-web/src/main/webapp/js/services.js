'use strict';

/* Services */
angular.module('armonitor.services', ['ngResource'])
		.factory('DashboardRSService', function($resource, config) {
			return $resource(config.server + '/db', {}, {
				get: {
					method: 'GET',
					isArray: false
				},
				build: {
					method: 'GET',
					url: config.server + '/db/sys/:sys/build',
					params: {sys: '@sys'},
					isArray: false
				}
			});
		})
		.factory('DashboardService', function(DashboardRSService) {
			var dashboard = null;

			function _load(callback) {
				DashboardRSService.get(function(response) {
					dashboard = response;
					if (callback) {
						callback(dashboard.size);
					}					
				});
			}

			_load();

			return {
				getProjects: function() {
					if (dashboard) {
						return dashboard.projects;
					}
					return [];
				},
				load: function(callback) {
					_load(callback);
				},
				get: function() {
					return dashboard;
				},				
				clear: function() {
					dashboard = null;
				}
			};
		})
		.value('version', '0.1');