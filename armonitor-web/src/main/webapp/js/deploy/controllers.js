controllers.controller('DeploymentSystemCtrl', ['$scope','DeployService',
	function($scope, DeployService) {
	
	function _startup() {
		DeployService.systems({}, function(response) {
			$scope.all = response;
		});
	}

	_startup();

	$scope.reload = function() {
		$scope.filter = null;
		_startup();
	};

	$scope.clear = function() {
		$scope.filter = null;
	};

	$scope.search = function(row) {
		if ($scope.filter) {
			var tmp = $scope.filter || '';
			return !!(((row.name !== null && row.name.indexOf(tmp)) !== -1
					|| (row.clazz !== null && row.clazz.indexOf(tmp) !== -1)
					|| (row.domain !== null && row.domain.indexOf(tmp) !== -1)
					|| (row.application !== null && row.application.indexOf(tmp) !== -1)
					|| (row.project !== null && row.project.indexOf(tmp) !== -1)
					));
		}
		return true;
	};
}]);