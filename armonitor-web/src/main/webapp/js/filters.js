'use strict';

/* Filters */

angular.module('armonitor.filters', [])
	.filter('filterSize', function() {
		return function(input, value) {
				value(input.length);
				return input;
		};
	});