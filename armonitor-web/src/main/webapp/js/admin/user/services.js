services.factory('UserAdminService', function($resource, config) {
	return $resource(config.server + '/user', {}, {
		save: {
			method: 'POST',
			url: config.server + '/user',
			isArray: false
		},
		pswd: {
			method: 'POST',
			url: config.server + '/user/:guid/password',
			params: {guid: '@guid'},
			isArray: false
		}
	});
})