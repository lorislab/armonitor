'use strict';

/* Controllers */
angular.module('armonitor.controllers', [])
		.controller('BTSAdminCtrl', function($scope) {
		})
		.controller('ProjectsAdminCtrl', function($scope) {
		})
		.controller('SCMAdminCtrl', function($scope, $routeParams, $location, SCMAdminService, CommonService) {

			$scope.data = null;
			$scope.types = null;

			function _startup() {
				SCMAdminService.types({}, function(response) {
					$scope.types = response;
				});
				if ($routeParams.guid) {
					SCMAdminService.get({guid: $routeParams.guid}, function(response) {
						$scope.data = response;
					});					
				} else {
					SCMAdminService.create({}, function(response) {
						$scope.data = response;
					});					
				}
			}
			
			_startup();
			
			$scope.close = function() {
				history.back();
				scope.$apply();	
			};
			
			$scope.save = function() {
				SCMAdminService.save({}, $scope.data, function(response) {
					$scope.data = response;
					CommonService.updateMsg();
				});
			};			

			$scope.delete = function() {
				SCMAdminService.delete({guid: $routeParams.guid}, function(response) {
					history.back();
					scope.$apply();
				});					
			};
		})
		.controller('SCMSearchAdminCtrl', function($scope, SCMAdminService) {

			$scope.all = null;
			$scope.filter = null;
			$scope.types = null;
			
			$scope.pswd = {
				o: null,
				n: null,
				c: null
			};

			function _startup() {
				SCMAdminService.types({}, function(response) {
					$scope.types = response;					
				});	
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
		.controller('TimerAdminCtrl', function($scope, TimerAdminService, CommonService) {

			$scope.data = null;
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
				TimerAdminService.save({}, $scope.data, function(response) {
					$scope.data = response;
					CommonService.updateMsg();
					_status();
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
		.controller('MailAdminCtrl', function($scope, MailAdminService, CommonService) {

			$scope.data = null;

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
					CommonService.updateMsg();
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
		.controller('ErrorCtrl', function($scope, ErrorService) {
			$scope.errors = null;

			$scope.$watch(function() {
				return ErrorService.errors();
			}, function(newVal, oldVal) {
				$scope.errors = newVal;
			});
			
			
			$scope.close = function(index) {
				ErrorService.close(index);
			};
		})
		.controller('MenuCtrl', function($scope, $location, $modal, CommonService) {

			$scope.user = null;

			$scope.roles = null;
				
			$scope.info = null;
			
			$scope.$watch(function() {
				return CommonService.info();
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
		});