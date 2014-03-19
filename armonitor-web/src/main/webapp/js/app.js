'use strict';


// Declare app level module which depends on filters, and services
var app = angular.module('armonitor', [
	'ngRoute',
	'ui.bootstrap',
	'pascalprecht.translate',
	'armonitor.filters',
	'armonitor.services',
	'armonitor.directives',
	'armonitor.controllers'
]);

app.provider('config', function() {
	var _server;
	return {
		setServer: function(value) {
			_server = value;
		},
		$get: function() {
			return {
				server: _server
			};
		}
	};
});

app.config(function(configProvider) {
	configProvider.setServer("http://localhost:8080/armonitor/rs");
});
app.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/dashboard', {templateUrl: 'partials/dashboard.html', controller: 'DashboardCtrl'});
		$routeProvider.when('/about', {templateUrl: 'partials/about.html', controller: 'AboutCtrl'});
		$routeProvider.when('/activity/:guid', {templateUrl: 'partials/activity.html', controller: 'ActivityCtrl'});
		$routeProvider.when('/systembuild/:guid', {templateUrl: 'partials/systembuild.html', controller: 'SystemBuildCtrl'});
		$routeProvider.when('/appbuild/:guid', {templateUrl: 'partials/appbuild.html', controller: 'ApplicationBuildCtrl'});
		$routeProvider.when('/versionbuild/:app/:ver', {templateUrl: 'partials/versionbuild.html', controller: 'VersionBuildCtrl'});
		$routeProvider.when('/profile', {templateUrl: 'partials/profile.html', controller: 'ProfileCtrl'});
		$routeProvider.when('/settings', {templateUrl: 'partials/admin/services.html', controller: 'ServicesCtrl'});		
		$routeProvider.when('/settings/timer', {templateUrl: 'partials/admin/services/timer.html', controller: 'TimerAdminCtrl'});
		$routeProvider.when('/settings/mail', {templateUrl: 'partials/admin/services/mail.html', controller: 'MailAdminCtrl'});
		$routeProvider.when('/settings/scm', {templateUrl: 'partials/admin/scm/search.html', controller: 'SCMSearchAdminCtrl'});		
		$routeProvider.when('/settings/scm/edit', {templateUrl: 'partials/admin/scm/edit.html', controller: 'SCMAdminCtrl'});
		$routeProvider.when('/settings/scm/edit/:guid', {templateUrl: 'partials/admin/scm/edit.html', controller: 'SCMAdminCtrl'});
		$routeProvider.when('/settings/bts', {templateUrl: 'partials/admin/bts/search.html', controller: 'BTSSearchAdminCtrl'});		
		$routeProvider.when('/settings/bts/edit', {templateUrl: 'partials/admin/bts/edit.html', controller: 'BTSAdminCtrl'});
		$routeProvider.when('/settings/bts/edit/:guid', {templateUrl: 'partials/admin/bts/edit.html', controller: 'BTSAdminCtrl'});		
		$routeProvider.when('/settings/project', {templateUrl: 'partials/admin/project/search.html', controller: 'ProjectSearchAdminCtrl'});		
		$routeProvider.when('/settings/project/edit', {templateUrl: 'partials/admin/project/edit.html', controller: 'ProjectAdminCtrl'});		
		$routeProvider.when('/settings/project/edit/:guid', {templateUrl: 'partials/admin/project/edit.html', controller: 'ProjectAdminCtrl'});		
		$routeProvider.when('/settings/services', {templateUrl: 'partials/admin/services.html', controller: 'ServicesCtrl'});
		$routeProvider.otherwise({redirectTo: '/dashboard'});
	}]);

app.config(['$translateProvider', function($translateProvider) {

		$translateProvider.translations('en', {
			'APP_NAME': 'Release monitor',
			'MENU_DASHBOARD': 'Dashboard',
			'MENU_ABOUT': 'About',
		});

		$translateProvider.useStaticFilesLoader({
			'prefix': 'locale\\',
			'suffix': '.json'
		});
		$translateProvider.preferredLanguage('en');
	}]);

// register the interceptor during module config
app.config(function($httpProvider) {

	$httpProvider.responseInterceptors.push(function($q, ErrorService) {

		return function(promise) {

			var _ok = function(value) {
				return value;
			};

			var _error = function(value) {
				if (value.status === 400) {					
					ErrorService.error(value.data);					
				}
				return $q.reject(value);
			};

			return promise.then(_ok, _error);

		};
	});
});

var services = angular.module('armonitor.services', ['ngResource']);
var controllers = angular.module('armonitor.controllers', []);
var filters = angular.module('armonitor.filters', []);
var directives = angular.module('armonitor.directives', []);