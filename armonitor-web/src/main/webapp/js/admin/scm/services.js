services.factory('SCMAdminService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/ad/scm', {}, {
		remove: {
			method: 'DELETE',
			url: config.server + '/ad/scm/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/scm',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/scm',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/scm/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		test: {
			method: 'GET',
			url: config.server + '/ad/scm/:guid/test',
			params: {guid: '@guid'},
			isArray: false
		},
		repo: {
			method: 'POST',
			url: config.server + '/ad/scm/:guid/test',
			params: {guid: '@guid'},
			isArray: false
		},		
		pswd: {
			method: 'POST',
			url: config.server + '/ad/scm/:guid/password',
			params: {guid: '@guid'},
			isArray: false
		},
		types: {
			method: 'GET',
			url: config.server + '/ad/scm/types',
			isArray: false
		},
		apps: {
			method: 'GET',
			url: config.server + '/ad/scm/:guid/app',
			params: {guid: '@guid'},
			isArray: true
		},
		app: {
			method: 'GET',
			url: config.server + '/ad/scm/:guid/app/:app',
			params: {guid: '@guid', app: '@app'},
			isArray: false
		},
		add: {
			method: 'PUT',
			url: config.server + '/ad/scm/:guid/app/:app',
			params: {guid: '@guid', app: '@app'},
			isArray: false
		},
		all: {
			method: 'GET',
			url: config.server + '/ad/scm',
			isArray: true
		},
		list: {
			method: 'GET',
			url: config.server + '/ad/scm/list',
			isArray: false
		}		
	});
}]);