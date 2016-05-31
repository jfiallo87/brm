(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityCatalogDialogController', FacilityCatalogDialogController);

    FacilityCatalogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilityCatalog', 'FacilityEventCatalog', 'FacilitySchedule'];

    function FacilityCatalogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilityCatalog, FacilityEventCatalog, FacilitySchedule) {
        var vm = this;

        vm.facilityCatalog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.facilityeventcatalogs = FacilityEventCatalog.query();
        vm.facilityschedules = FacilitySchedule.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilityCatalog.id !== null) {
                FacilityCatalog.update(vm.facilityCatalog, onSaveSuccess, onSaveError);
            } else {
                FacilityCatalog.save(vm.facilityCatalog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityCatalogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
