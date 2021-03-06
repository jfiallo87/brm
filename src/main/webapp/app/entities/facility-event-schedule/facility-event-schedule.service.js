(function() {
    'use strict';
    angular
        .module('brmApp')
        .factory('FacilityEventSchedule', FacilityEventSchedule);

    FacilityEventSchedule.$inject = ['$resource', 'DateUtils'];

    function FacilityEventSchedule ($resource, DateUtils) {
        var resourceUrl =  'api/facility-event-schedules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.start = DateUtils.convertDateTimeFromServer(data.start);
                        data.end = DateUtils.convertDateTimeFromServer(data.end);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
