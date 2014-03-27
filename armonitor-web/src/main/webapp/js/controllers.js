'use strict';

/* Controllers */
controllers.controller('LoginModalCtrl', ['$scope', '$modalInstance', 'CommonService', 
	function($scope, $modalInstance, CommonService) {
		
	$scope.msg = { error: null };
	$scope.data = { email: null, password: null };

	$scope.login = function() {
		$scope.msg.error = null;
		CommonService.login($scope.data, function(user) {
			if (user) {
				$modalInstance.close(user);
			}
		}, function(response) {
			$scope.msg.error = response.data;
		});
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
}]);

controllers.controller('DashboardCtrl',['$scope', 'DashboardRSService', 
	function($scope, DashboardRSService) {

	$scope.dashboard = null;

	function _load() {
		$scope.dashboard = null;
		DashboardRSService.get(function(response) {
			$scope.dashboard = response;
		});
	}

	_load();

	function _findEntity(list, guid) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].guid === guid) {
				return list[i];
			}
		}
		return null;
	}

	function _findEntityIndex(list, guid) {
		for (var i = 0; i < list.length; i++) {
			if (list[i].guid === guid) {
				return i;
			}
		}
		return null;
	}

	$scope.updateBuild = function(sys) {
		DashboardRSService.updateBuild({sys: sys}, function(res) {
			if (res) {
				var p = _findEntity($scope.dashboard.projects, res.project);
				if (p !== null) {
					var a = _findEntity(p.applications, res.application);
					if (a !== null) {
						var i = _findEntityIndex(a.systems, res.guid);
						if (i !== null) {
							a.systems[i] = res;
						}
					}
				}
			}
		});
	};
	$scope.reload = function() {
		$scope.dashboard = null;
		DashboardRSService.reload(function(response) {
			$scope.dashboard = response;
		});
	};

	$scope.closeMsg = function() {
		DashboardRSService.msg();
		$scope.dashboard.msg = true;
	};

}]);
controllers.controller('VersionBuildCtrl',['$scope','$routeParams','VersionBuildRSService','BuildRSService','CommonService', 
	function($scope, $routeParams, VersionBuildRSService, BuildRSService, CommonService) {

	$scope.ver = null;
	$scope.version = null;
	$scope.build = null;
	$scope.other = false;
	$scope.manifest = false;


	function _load() {
		$scope.app = null;
		$scope.build = null;
		VersionBuildRSService.get({app: $routeParams.app, ver: $routeParams.ver}, function(response) {
			$scope.ver = response;
			$scope.version = $routeParams.ver;
		});
	}

	$scope.reload = function() {
		$scope.ver = null;
		$scope.build = null;
		VersionBuildRSService.reload({app: $routeParams.app, ver: $routeParams.ver}, function(response) {
			$scope.ver = response;
			$scope.version = $routeParams.ver;
		});
	};

	$scope.timelineSelect = function(item) {
		if (item) {
			BuildRSService.get({guid: item.guid}, function(response) {
				$scope.build = response;
				if ($scope.build) {
					$scope.other = CommonService.check($scope.build.other);
					$scope.manifest = CommonService.check($scope.build.manifest);
				}
			});
		} else {
			$scope.build = null;
			$scope.other = false;
			$scope.manifest = false;
			$scope.$apply();
		}
	};

	_load();
}]);

controllers.controller('ApplicationBuildCtrl', ['$scope','$routeParams','ApplicationBuildRSService','BuildRSService','CommonService',
	function($scope, $routeParams, ApplicationBuildRSService, BuildRSService, CommonService) {

	$scope.app = null;
	$scope.build = null;
	$scope.other = false;
	$scope.manifest = false;

	function _load() {
		$scope.app = null;
		$scope.build = null;
		ApplicationBuildRSService.get({guid: $routeParams.guid}, function(response) {
			$scope.app = response;
		});
	}

	$scope.reload = function() {
		$scope.app = null;
		$scope.build = null;
		ApplicationBuildRSService.reload({guid: $routeParams.guid}, function(response) {
			$scope.app = response;
		});
	};

	$scope.timelineSelect = function(item) {
		if (item) {
			BuildRSService.get({guid: item.guid}, function(response) {
				$scope.build = response;
				if ($scope.build) {
					$scope.other = CommonService.check($scope.build.other);
					$scope.manifest = CommonService.check($scope.build.manifest);
				}
			});
		} else {
			$scope.build = null;
			$scope.other = false;
			$scope.manifest = false;
			$scope.$apply();
		}
	};

	_load();
}]);

controllers.controller('SystemBuildCtrl', ['$scope','$routeParams','SystemBuildRSService','BuildRSService','CommonService',
	function($scope, $routeParams, SystemBuildRSService, BuildRSService, CommonService) {

	$scope.system = null;
	$scope.build = null;
	$scope.other = false;
	$scope.manifest = false;

	function _load() {
		$scope.system = null;
		$scope.build = null;
		SystemBuildRSService.get({guid: $routeParams.guid}, function(response) {
			$scope.system = response;
		});
	}

	$scope.reload = function() {
		$scope.system = null;
		$scope.build = null;
		SystemBuildRSService.reload({guid: $routeParams.guid}, function(response) {
			$scope.system = response;
		});
	};

	$scope.timelineSelect = function(item) {
		if (item) {
			BuildRSService.get({guid: item.guid}, function(response) {
				$scope.build = response;
				if ($scope.build) {
					$scope.other = CommonService.check($scope.build.other);
					$scope.manifest = CommonService.check($scope.build.manifest);
				}
			});
		} else {
			$scope.build = null;
			$scope.other = false;
			$scope.manifest = false;
			$scope.$apply();
		}
	};

	_load();
}]);

controllers.controller('ActivityCtrl', ['$scope','$routeParams','ActivityRSService',
	function($scope, $routeParams, ActivityRSService) {

	$scope.old = null;
	$scope.activity = null;
	$scope.now = null;
	$scope.bcSize = 0;
	$scope.cSize = 0;
	$scope.stypes = [];
	$scope.ftypes = [];
	$scope.radio = 'old';

	$scope.ra = false;

	$scope.$watch('radio', function() {
		$scope.activity = null;
		if ($scope.radio === 'old') {
			_result($scope.old);
		} else {
			if ($scope.now) {
				_result($scope.now);
			} else {
				_loadNow();
			}
		}
	});

	function _loadNow() {
		$scope.ra = true;
		ActivityRSService.now({guid: $routeParams.guid}, function(response) {
			_result(response);
			$scope.now = response;
			$scope.ra = false;
		}, function(error) {
			$scope.ra = false;
		});
	}

	function _load() {
		_clear();
		ActivityRSService.get({guid: $routeParams.guid}, function(response) {
			_result(response);
			$scope.old = response;
		});
	}

	function _clear() {
		$scope.activity = null;
		$scope.filterBuild = null;
		$scope.filterVersion = null;
	}

	function _result(response) {
		$scope.stypes = [];
		$scope.ftypes = [];
		$scope.activity = response;
		if ($scope.activity) {
			$scope.ftypes = angular.copy($scope.activity.types);
			if ($scope.ftypes) {
				var t;
				for (t in $scope.ftypes) {
					$scope.stypes.push({"id": $scope.ftypes[t], "select": true});
				}
			}
		}
	}

	_load();

	$scope.bcSizeUpdate = function(size) {
		$scope.bcSize = size;
	};

	$scope.cSizeUpdate = function(size) {
		$scope.cSize = size;
	};

	$scope.updateTypes = function(t) {
		var i = $scope.ftypes.indexOf(t);
		if (i === -1) {
			$scope.ftypes.push(t);
		} else {
			$scope.ftypes.splice(i, 1);
		}
	};

	$scope.reload = function() {
		_clear();
		$scope.ra = true;
		ActivityRSService.reloadNow({guid: $routeParams.guid}, function(response) {
			_result(response);
			$scope.now = response;
			$scope.ra = false;
		}, function(error) {
			$scope.ra = false;
		});
	};

	$scope.searchBuild = function(row) {
		if ($scope.filterBuild) {
			var tmp = $scope.filterBuild || '';
			return !!(((row.id !== null && row.id.indexOf(tmp)) !== -1
					|| (row.assignee !== null && row.assignee.indexOf(tmp) !== -1)
					|| (row.summary !== null && row.summary.indexOf(tmp) !== -1)));
		}
		return true;
	};

	$scope.clearBuild = function() {
		$scope.filterBuild = null;
	};

	$scope.clearVersion = function() {
		$scope.filterVersion = null;
	};

	$scope.searchVersion = function(row) {
		if ($scope.filterVersion) {
			var tmp = $scope.filterVersion || '';
			return !!(((row.id !== null && row.id.indexOf(tmp)) !== -1
					|| (row.assignee !== null && row.assignee.indexOf(tmp) !== -1)
					|| (row.summary !== null && row.summary.indexOf(tmp) !== -1)));
		}
		return true;
	};

	$scope.search = function(row) {
		return !!(($scope.ftypes.indexOf(row.type) !== -1) || row.type === null);
	};
}]);

controllers.controller('ProfileCtrl', ['$scope','CommonService','SecurityRSService', 
	function($scope, CommonService, SecurityRSService) {

	$scope.pswd = {n: null, c: null, o: null};
	$scope.user = angular.copy(CommonService.user());

	if (!($scope.user)) {
		CommonService.logout();
	}

	$scope.validation = false;

	$scope.pswd = {o: null, n: null, c: null};

	$scope.save = function() {
		CommonService.update($scope.user, function(response) {

		});
	};

	$scope.changepswd = function() {
		$scope.validation = false;

		if ($scope.pswd.n && $scope.pswd.c && ($scope.pswd.n === $scope.pswd.c)) {
			SecurityRSService.pswd({guid: $scope.user.guid}, {old: $scope.pswd.o, p1: $scope.pswd.n}, function(response) {
				CommonService.logout();
			});
		} else {
			$scope.validation = true;
		}
	};

}]);

controllers.controller('ErrorCtrl', ['$scope','ErrorService', 
	function($scope, ErrorService) {

	$scope.$watch(function() {
		return ErrorService.error();
	}, function(newVal, oldVal) {
		$scope.error = newVal;		
	});
	
	$scope.close = function() {
		ErrorService.close();
	};	

}]);

controllers.controller('MenuCtrl', ['$scope','$location','$modal','CommonService','ErrorService', 
	function($scope, $location, $modal, CommonService, ErrorService) {


	$scope.$watch(function() {
		return ErrorService.info();
	}, function(newVal, oldVal) {
		$scope.info = newVal;
	});

	$scope.$watch(function() {
		return CommonService.user();
	}, function(newVal, oldVal) {
		$scope.user = newVal;
	});

	$scope.$watch(function() {
		return CommonService.roles();
	}, function(newVal, oldVal) {
		$scope.roles = newVal;
	});

	$scope.close = function() {
		CommonService.closeMsg();
	};

	$scope.logout = function() {
		CommonService.logout();
	};

	$scope.login = function() {

		var modalInstance = $modal.open({
			templateUrl: 'partials/include/login.html',
			controller: 'LoginModalCtrl'
		});

		modalInstance.result.then(function(user) {
			// login
		}, function() {
			// close modal
		});
	};

	$scope.active = function(data) {
		var tmp = $location.path();
		return tmp.indexOf(data) !== -1;
	};
}]);