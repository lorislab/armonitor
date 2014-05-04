controllers.controller('DeploymentCtrl', ['$scope', '$routeParams', 'DeployService',
	function($scope, $routeParams, DeployService) {
		
	function _startup() {
		DeployService.build({guid: $routeParams.guid, build: $routeParams.build}, function(response) {
			$scope.data = response;
		});
	}

	_startup();
	
	$scope.reload = function() {
		DeployService.build({guid: $routeParams.guid, build: $routeParams.build, reload: true}, function(response) {
			$scope.data = response;
		});
	};	
	
	$scope.deploy = function() {
		DeployService.deploy({system: $routeParams.guid, build: $routeParams.build, notification: true}, function(response) {
			$scope.data = response;
		});		
	};
}]);
	
controllers.controller('DeploymentSystemBuildsCtrl', ['$scope', '$routeParams', 'DeployService',
	function($scope, $routeParams, DeployService) {
		
	function _startup() {
		DeployService.builds({guid: $routeParams.guid}, function(response) {
			$scope.data = response;
		});
	}

	_startup();	
	
	$scope.reload = function() {
		$scope.filter = null;
		DeployService.builds({guid: $routeParams.guid, reload: true}, function(response) {
			$scope.data = response;
		});
	};

	$scope.clear = function() {
		$scope.filter = null;
	};

	$scope.search = function(row) {
		if ($scope.filter) {
			var tmp = $scope.filter || '';
			return !!(((row.scm !== null && row.scm.indexOf(tmp)) !== -1
					|| (row.mavenVersion !== null && row.mavenVersion.indexOf(tmp) !== -1)
					|| (row.build !== null && row.build.indexOf(tmp) !== -1)
					|| (row.groupId !== null && row.groupId.indexOf(tmp) !== -1)
					|| (row.artifactId !== null && row.artifactId.indexOf(tmp) !== -1)
					));
		}
		return true;
	};	
}]);

controllers.controller('DeploymentSystemCtrl', ['$scope','DeployService',
	function($scope, DeployService) {
	
	function _startup() {
		DeployService.systems({}, function(response) {
			$scope.all = response;
		});
	}

	_startup();
	
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