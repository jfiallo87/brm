(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('EventCatalog', EventCatalog);

    EventCatalog.$inject = ['$resource'];

    function EventCatalog ($resource) {
        var resourceUrl =  'api/event-catalogs/:id';

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
