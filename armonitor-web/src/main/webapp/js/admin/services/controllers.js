controllers.controller('TimerAdminCtrl', ['$scope','TimerAdminService','CommonService',
	function($scope, TimerAdminService, CommonService) {

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
			CommonService.updateMsg();
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

controllers.controller('MailAdminCtrl', ['$scope','MailAdminService','CommonService',
	function($scope, MailAdminService, CommonService) {

	MailAdminService.get({}, function(response) {
		$scope.data = response;
	});

	$scope.save = function() {
		MailAdminService.save({}, $scope.data, function(response) {
			$scope.data = response;
			CommonService.updateMsg();
		});
	};
}]);