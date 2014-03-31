'use strict';


directives.directive('appVersion', ['version', function(version) {
				return function(scope, elm, attrs) {
					elm.text(version);
				};
  }]);
  
directives.directive('buildinfo', function() {
	return {
		restrict: 'EA',
		templateUrl: 'partials/include/buildinfo.html'
	};
});

directives.directive('timeline', function() {

	return {
		restrict: 'A',
		scope: {
			model: '=timeline',
			options: '=timelineOptions'
		},
		link: function($scope, $element) {
			var timeline = new links.Timeline($element[0]);
			links.events.addListener(timeline, 'select', function() {
				var sel = timeline.getSelection();
				if (sel[0]) {
					$scope.$parent.timelineSelect($scope.model[sel[0].row]);
				} else {
					$scope.$parent.timelineSelect(null);
				}
			});

			$scope.$watch('model', function(newVal, oldVal) {
				timeline.setData(newVal);
				timeline.setVisibleChartRangeAuto();
			});

			$scope.$watch('options', function(newVal, oldVal) {
				timeline.draw($scope.model, $scope.options);
			});
		}
	};
});
