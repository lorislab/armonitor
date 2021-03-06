services.factory('RoleAdminService', ['$resource','config',
	function($resource, config) {
	return $resource(config.server + '/ad/role', {}, {
		remove: {
			method: 'DELETE',
			url: config.server + '/ad/role/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		create: {
			method: 'PUT',
			url: config.server + '/ad/role',
			isArray: false
		},
		save: {
			method: 'POST',
			url: config.server + '/ad/role',
			isArray: false
		},
		get: {
			method: 'GET',
			url: config.server + '/ad/role/:guid',
			params: {guid: '@guid'},
			isArray: false
		},
		map: {
			method: 'GET',
			url: config.server + '/ad/role/map',
			isArray: false
		},	
		all: {
			method: 'GET',
			url: config.server + '/ad/role',
			isArray: true
		}	
	});
}]);	