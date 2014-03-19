'use strict';

filters.filter('filterSize', function() {
	return function(input, value) {
		if (input) {
			value(input.length);
		}
		return input;
	};
});