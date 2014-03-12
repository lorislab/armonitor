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
		$routeProvider.when('/admin', {templateUrl: 'partials/admin/admin.html', controller: 'AboutCtrl'});
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
