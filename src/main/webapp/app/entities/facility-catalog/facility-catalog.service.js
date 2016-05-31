(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('FacilityCatalog', FacilityCatalog);

    FacilityCatalog.$inject = ['$resource'];

    function FacilityCatalog ($resource) {
        var resourceUrl =  'api/facility-catalogs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
