'use strict';

/* Controllers */
angular.module('armonitor.controllers', [])
		.controller('BTSAdminCtrl', function($scope) {	})
		.controller('ProjectsAdminCtrl', function($scope) {	})
		.controller('SCMAdminCtrl', function($scope, SCMAdminService) {

			$scope.all = null;
			$scope.filter = null;
			$scope.selected = null;
			$scope.types = null;
			$scope.apps = null;
			
			$scope.validation = false;

			$scope.pswd = {
				o: null,
				n: null,
				c: null
			};
			
			function _startup() {
				SCMAdminService.all({}, function(response) {
					$scope.all = response;
				});
			}

			_startup();

			$scope.reload = function() {
				_startup();
			};
			
			$scope.clear = function() {
				$scope.filter = null;
			};

			$scope.search = function(row) {
				if ($scope.filter) {
					var tmp = $scope.filter || '';
					return !!((row.user.indexOf(tmp) !== -1 || row.server.indexOf(tmp) !== -1));
				}
				return true;
			};

			$scope.switch = function(item) {
				if (!$scope.types) {
					SCMAdminService.types({}, function(response) {
						$scope.types = response;
					});						
				}
				SCMAdminService.get({guid: item.guid}, function(response) {
					$scope.selected = response;
					if ($scope.selected) {
						SCMAdminService.apps({guid: item.guid}, function(response) {
							$scope.apps = response;
						});
					}
				});				
			};
			
			$scope.close = function() {
				$scope.selected = null;
			};
			
			$scope.save = function() {
				SCMAdminService.save({}, $scope.selected, function(response) {
					$scope.data = response;
				});
			};
			
			$scope.changepswd = function() {
				$scope.validation = false;

				if ($scope.pswd.n && $scope.pswd.c && ($scope.pswd.n === $scope.pswd.c)) {
					SCMAdminService.pswd({guid: $scope.user.guid}, {old: $scope.pswd.o, p1: $scope.pswd.n}, function(response) {
						
					});
				} else {
					$scope.validation = true;
				}
			};
		})
		.controller('TimerAdminCtrl', function($scope, TimerAdminService) {

			$scope.data = null;
			$scope.error = false;
			$scope.status = null;

			function _startup() {
				TimerAdminService.get({}, function(response) {
					$scope.data = response;
					_status();
				});
			}

			function _status() {
				$scope.status = null;
				TimerAdminService.status({}, function(response, status) {
					$scope.status = response;
				});
			}

			_startup();

			$scope.save = function() {
				$scope.error = false;
				TimerAdminService.save({}, $scope.data, function(response) {
					$scope.data = response;
					_status();
				}, function(response) {
					$scope.error = true;
				});
			};

			$scope.start = function() {
				TimerAdminService.start({}, function(response, status) {
					$scope.status = response;
				});
			};

			$scope.stop = function() {
				TimerAdminService.stop({}, function(response, status) {
					$scope.status = response;
				});
			};
		})
		.controller('MailAdminCtrl', function($scope, MailAdminService) {

			$scope.data = null;
			$scope.error = false;

			function _startup() {
				MailAdminService.get({}, function(response) {
					$scope.data = response;
				});
			}

			_startup();

			$scope.save = function() {
				$scope.error = false;
				MailAdminService.save({}, $scope.data, function(response) {
					$scope.data = response;
				}, function(response) {
					$scope.error = true;
				});
			};
		})
		.controller('LoginModalCtrl', function($scope, $modalInstance, CommonService) {

			$scope.msg = {
				error: false
			};

			$scope.data = {
				email: null,
				password: null
			};

			$scope.login = function() {
				$scope.msg.error = false;
				$scope.data.email = 'andrej@ajka-andrej.com';
				$scope.data.password = 'test';
				CommonService.login($scope.data, function(user) {
					if (user) {
						$modalInstance.close(user);
					} else {
						$scope.msg.error = true;
					}
				});
			};

			$scope.cancel = function() {
				$modalInstance.dismiss('cancel');
			};
		})
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
		.controller('ServicesCtrl', function($scope) {

			$scope.menu = 'timer';

			$scope.change = function(item) {
				$scope.menu = item;
			};

			$scope.page = function() {
				return 'partials/admin/services/' + $scope.menu + '.html';
			};
		})
		.controller('ProfileCtrl', function($scope, CommonService, SecurityRSService) {

			$scope.user = angular.copy(CommonService.user());

			if (!($scope.user)) {
				CommonService.logout();
			}

			$scope.validation = false;

			$scope.pswd = {
				o: null,
				n: null,
				c: null
			};

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

		})
		.controller('MenuCtrl', function($scope, $location, $modal, CommonService) {


			$scope.user = null;

			$scope.roles = null;

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
//				var r = false;
//				for (var i = 0; i < data.length && !r; i++) {
//					r = (tmp.indexOf(data[i]) === 0);
//				}
				return tmp.indexOf(data) !== -1;
			};
		});