(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventServiceCatalogDialogController', FacilityEventServiceCatalogDialogController);

    FacilityEventServiceCatalogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilityEventServiceCatalog', 'ServiceCatalog', 'FacilityEventCatalog', 'FacilityEventServiceReservation'];

    function FacilityEventServiceCatalogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilityEventServiceCatalog, ServiceCatalog, FacilityEventCatalog, FacilityEventServiceReservation) {
        var vm = this;

        vm.facilityEventServiceCatalog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.servicecatalogs = ServiceCatalog.query();
        vm.facilityeventcatalogs = FacilityEventCatalog.query();
        vm.facilityeventservicereservations = FacilityEventServiceReservation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilityEventServiceCatalog.id !== null) {
                FacilityEventServiceCatalog.update(vm.facilityEventServiceCatalog, onSaveSuccess, onSaveError);
            } else {
                FacilityEventServiceCatalog.save(vm.facilityEventServiceCatalog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityEventServiceCatalogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
