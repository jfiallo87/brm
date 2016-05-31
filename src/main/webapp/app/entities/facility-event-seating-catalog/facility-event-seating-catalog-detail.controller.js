(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventSeatingCatalogDetailController', FacilityEventSeatingCatalogDetailController);

    FacilityEventSeatingCatalogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilityEventSeatingCatalog', 'SeatingCatalog', 'FacilityEventCatalog', 'FacilityEventReservation'];

    function FacilityEventSeatingCatalogDetailController($scope, $rootScope, $stateParams, entity, FacilityEventSeatingCatalog, SeatingCatalog, FacilityEventCatalog, FacilityEventReservation) {
        var vm = this;

        vm.facilityEventSeatingCatalog = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityEventSeatingCatalogUpdate', function(event, result) {
            vm.facilityEventSeatingCatalog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
