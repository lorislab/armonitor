'use strict';

/* Controllers */

angular.module('armonitor.controllers', [])
		.controller('DashboardCtrl', function($scope, DashboardRSService, BuildCriteriaService) {

			$scope.dashboard = null;

			function _load() {
				DashboardRSService.get(function(response) {
					$scope.dashboard = response;
				});
			}

			_load();

			$scope.builds = function(app, build) {
				BuildCriteriaService.set(app, build);
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
		.controller('BuildsCtrl', function($scope, BuildCriteriaService, BuildRSService) {
			
			$scope.builds = [];
			$scope.app = null;
			$scope.build = null;
			
			function _load() {
				$scope.app = BuildCriteriaService.app();
				$scope.build = BuildCriteriaService.build();
				BuildRSService.search({}, BuildCriteriaService.criteria(), function(response) {
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