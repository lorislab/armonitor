controllers.controller('TimerAdminCtrl', ['$scope','TimerAdminService',
	function($scope, TimerAdminService) {

	TimerAdminService.get({}, function(response) {
		$scope.data = response;
		_status();
	});

	function _status() {
		$scope.status = null;
		TimerAdminService.status({}, function(response) {
			$scope.status = response;
		});
	}

	$scope.save = function() {
		TimerAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
			_status();
		});
	};

	$scope.start = function() {
		TimerAdminService.start({}, function(response) {
			$scope.status = response;
		});
	};

	$scope.stop = function() {
		TimerAdminService.stop({}, function(response) {
			$scope.status = response;
		});
	};
}]);

controllers.controller('MailAdminCtrl', ['$scope','MailAdminService',
	function($scope, MailAdminService) {

	MailAdminService.get({}, function(response) {
		$scope.data = response;
	});

	$scope.save = function() {
		MailAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
		});
	};
	
	$scope.test = function() {
		MailAdminService.test({email: $scope.test.email}, function(response) {
			// do nothing
		});
		$scope.test.email = null;
	};	
}]);