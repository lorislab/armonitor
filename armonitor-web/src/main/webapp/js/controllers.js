'use strict';

/* Controllers */
angular.module('armonitor.controllers', [])
		.controller('DashboardCtrl', function($scope, DashboardRSService) {

			$scope.dashboard = null;
	
			function _load() {
				$scope.dashboard = null;
				DashboardRSService.get(function(response) {
					$scope.dashboard = response;
				});
			}

			_load();

			$scope.updateBuild = function(sys) {
				DashboardRSService.updateBuild({sys: sys}, function(res) {
					if (res) {
						var p = $scope.dashboard.projects[res.project];
						var a = p.applications[res.application];
						a.systems[res.guid] = res;
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

			$scope.getProjects = function() {
				if ($scope.dashboard) {
					return $scope.dashboard.projects;
				}
				return [];
			};
		})	
		.controller('VersionBuildCtrl', function($scope, $routeParams, VersionBuildRSService, BuildRSService, CommonService) {
			
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
		})			
		.controller('ApplicationBuildCtrl', function($scope, $routeParams, ApplicationBuildRSService, BuildRSService, CommonService) {
			
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
		})		
		.controller('SystemBuildCtrl', function($scope, $routeParams, SystemBuildRSService, BuildRSService, CommonService) {
			
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
		})
		.controller('ActivityCtrl', function($scope, $routeParams, ActivityRSService) {

			$scope.activity = null;
			$scope.bcSize = 0;
			$scope.cSize = 0;			
			$scope.stypes = [];			
			$scope.ftypes = [];
			
			function _load() {
				_clear();
				ActivityRSService.get({guid: $routeParams.guid}, function(response) {
					_result(response);
				});
			}

			function _clear() {
				$scope.stypes = [];
				$scope.ftypes = [];
				$scope.activity = null;
			}
			
			function _result(response) {
					$scope.activity = response;
					if ($scope.activity) {
						$scope.ftypes = $scope.activity.types;
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
				ActivityRSService.reload({guid: $routeParams.guid}, function(response) {
					_result(response);
				});
			};
			
			$scope.search = function(row) {
				return !!(($scope.ftypes.indexOf(row.type) !== -1) || row.type === null);
			};			
		})
		.controller('AboutCtrl', function($scope) {

		})	
		.controller('MenuCtrl', function($scope, $location) {

			$scope.active = function(data) {
				var tmp = $location.path();
				var r = false;
				for (var i = 0; i < data.length && !r; i++) {
					r = (tmp.indexOf(data[i]) === 0);
				}
				return r;
			};
		});