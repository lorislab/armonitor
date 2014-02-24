'use strict';

/* Directives */


angular.module('armonitor.directives', [])
		.directive('timeline', function() {

			return {
				restrict: 'A',
				scope: {
					model: '=timeline',
					options: '=timelineOptions',
					selection: '=timelineSelection',
					timelineSelect: '=timelineSelect'
				},
				link: function($scope, $element) {
					var timeline = new links.Timeline($element[0]);
					links.events.addListener(timeline, 'select', function() {
						$scope.selection = undefined;
						var sel = timeline.getSelection();
						if (sel[0]) {
							$scope.selection = $scope.model[sel[0].row];
						}
					});

					$scope.$watch('model', function(newVal, oldVal) {
						timeline.setData(newVal);
						timeline.setVisibleChartRangeAuto();
					});

					$scope.$watch('options', function(newVal, oldVal) {
						timeline.draw($scope.model, $scope.options);
					});

					$scope.$watch('selection', function(newVal, oldVal) {
						if (!angular.equals(newVal, oldVal)) {
							for (var i = $scope.model.length - 1; i >= 0; i--) {
								if (angular.equals($scope.model[i], newVal)) {
									timeline.setSelection([{
											row: i
										}]);
									break;
								}
							}
							;
						}
					});
				}
			};
		})
		.directive('appVersion', ['version', function(version) {
				return function(scope, elm, attrs) {
					elm.text(version);
				};
			}]);
