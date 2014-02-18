'use strict';

/* Controllers */

angular.module('armonitor.controllers', [])
		.controller('DashboardCtrl', function($scope, DashboardService) {

			$scope.getDashboard = function() {
				return DashboardService.get();
			};
			$scope.getProjects = function() {
				return DashboardService.getProjects();
			};		
		})
		.controller('AboutCtrl', function($scope) {

		})
		.controller('MenuCtrl', function($scope, $location) {
			
			$scope.active = function(data) {
				var tmp = $location.path();
				var r = false;
				for (var i=0; i<data.length && !r; i++) {
					r = (tmp.indexOf(data[i]) === 0);
				}
				return r;
			};
		});