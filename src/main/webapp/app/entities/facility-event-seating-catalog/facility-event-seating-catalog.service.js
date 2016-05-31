(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('FacilityEventSeatingCatalog', FacilityEventSeatingCatalog);

    FacilityEventSeatingCatalog.$inject = ['$resource'];

    function FacilityEventSeatingCatalog ($resource) {
        var resourceUrl =  'api/facility-event-seating-catalogs/:id';

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
