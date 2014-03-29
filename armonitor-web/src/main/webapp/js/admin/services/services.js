services.factory('NotificationAdminService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/ad/notify', {}, {
		system: {
			method: 'GET',
			url: config.server + '/ad/notify/sys/:guid',
			params: {guid: '@guid'},
			isArray: false
		}
	});
}]);

services.factory('MailAdminService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/ad/mail', {}, {
		save: {
			method: 'POST',
			url: config.server + '/ad/mail/cf',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/mail/cf',
			isArray: false
		},
		test: {
			method: 'GET',
			url: config.server + '/ad/mail/test/:email',
			params: {email: '@email'},
			isArray: false
		}		
	});
}]);

services.factory('TimerAdminService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/ad/timer', {}, {
		start: {
			method: 'GET',
			url: config.server + '/ad/timer/start',
			isArray: false
		},
		stop: {
			method: 'GET',
			url: config.server + '/ad/timer/stop',
			isArray: false
		},
		status: {
			method: 'GET',
			url: config.server + '/ad/timer/status',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/timer/cf',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/timer/cf',
			isArray: false
		}
	});
}]);