(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('FacilityEventServiceCatalog', FacilityEventServiceCatalog);

    FacilityEventServiceCatalog.$inject = ['$resource'];

    function FacilityEventServiceCatalog ($resource) {
        var resourceUrl =  'api/facility-event-service-catalogs/:id';

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
