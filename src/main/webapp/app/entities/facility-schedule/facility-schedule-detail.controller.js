(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityScheduleDetailController', FacilityScheduleDetailController);

    FacilityScheduleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilitySchedule', 'FacilityCatalog'];

    function FacilityScheduleDetailController($scope, $rootScope, $stateParams, entity, FacilitySchedule, FacilityCatalog) {
        var vm = this;

        vm.facilitySchedule = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityScheduleUpdate', function(event, result) {
            vm.facilitySchedule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
