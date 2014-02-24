'use strict';

/* Controllers */

angular.module('armonitor.controllers', [])
		.controller('DashboardCtrl', function($scope, DashboardRSService) {

			$scope.dashboard = null;

			function _load() {
				DashboardRSService.get(function(response) {
					$scope.dashboard = response;
				});
			}

			_load();

			$scope.appBuilds = function(app) {
				DashboardRSService.setDashboardBuilds({}, {project: app.project, app: app.guid, version: null});
			};
			$scope.builds = function(app, build) {
				DashboardRSService.setDashboardBuilds({}, {project: app.project, app: app.guid, version: build.mavenVersion});
			};
			$scope.updateBuild = function(sys) {
				DashboardRSService.updateBuild({sys: sys}, function(res) {
					if (res) {
						var p = $scope.dashboard.projects[res.project];
						var a = p.applications[res.application];
						a.systems[res.guid] = res;
					}
				});
			};
			$scope.reload = function() {
				DashboardRSService.reload(function(response) {
					$scope.dashboard = response;
				});
			};

			$scope.closeMsg = function() {
				DashboardRSService.msg();
				$scope.dashboard.msg = true;
			};

			$scope.getProjects = function() {
				if ($scope.dashboard) {
					return $scope.dashboard.projects;
				}
				return [];
			};
		})
		.controller('AboutCtrl', function($scope) {

		})
		.controller('BuildsCtrl', function($scope, DashboardRSService, BuildRSService) {

			$scope.options = {
				'width': '100%',
				'height': 'auto',
				'groupsOrder': true,
				'editable': false
			};

			$scope.builds = [];
			$scope.app;

			$scope.reload = function() {
				_loadBuilds();
			};


			function _load() {
				DashboardRSService.getApp({}, function(response) {
					$scope.app = response;

					if ($scope.app) {
						_loadBuilds();
					}
				});

			}

			function _loadBuilds() {
				DashboardRSService.getDashboardBuilds({}, function(response) {
					$scope.builds = response;
				});
			}

			_load();

		})
		.controller('MenuCtrl', function($scope, $location) {

			$scope.active = function(data) {
				var tmp = $location.path();
				var r = false;
				for (var i = 0; i < data.length && !r; i++) {
					r = (tmp.indexOf(data[i]) === 0);
				}
				return r;
			};
		});