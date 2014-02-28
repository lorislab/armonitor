'use strict';

/* Controllers */

angular.module('armonitor.controllers', [])
		.controller('DashboardCtrl', function($scope, DashboardRSService) {

			$scope.dashboard = null;

			function _load() {
				$scope.dashboard = null;
				DashboardRSService.get(function(response) {
					$scope.dashboard = response;
				});
			}

			_load();

			$scope.appBuilds = function(app) {
				DashboardRSService.setDashboardBuilds({}, {project: app.project, app: app.guid, version: null});
			};
			$scope.builds = function(app, version) {
				DashboardRSService.setDashboardBuilds({}, {project: app.project, app: app.guid, version: version});
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
				$scope.dashboard = null;
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
		.controller('ActivityCtrl', function($scope, $routeParams, ActivityRSService) {

			$scope.activity = null;

			function _load() {
				$scope.activity = null;
				ActivityRSService.get({guid: $routeParams.guid}, function(response) {
					$scope.activity = response;
				});
			}

			_load();

			$scope.reload = function() {
				$scope.activity = null;
				ActivityRSService.reload({guid: $routeParams.guid}, function(response) {
					$scope.activity = response;
				});
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
			$scope.build = null;
			$scope.other = false;
			$scope.manifest = false;

			function _check(item) {
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
			
			$scope.timelineSelect = function(item) {
				if (item) {
					BuildRSService.get({guid: item.guid}, function(response) {
						$scope.build = response;
						if ($scope.build) {
							$scope.other = _check($scope.build.other);
							$scope.manifest = _check($scope.build.manifest);
						}
					});
				} else {
					$scope.build = null;
					$scope.other = false;
					$scope.manifest = false;
					$scope.$apply();
				}
			};

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
				$scope.build = null;
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