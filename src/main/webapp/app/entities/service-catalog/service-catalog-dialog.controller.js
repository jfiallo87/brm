(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('ServiceCatalogDialogController', ServiceCatalogDialogController);

    ServiceCatalogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiceCatalog', 'FacilityEventServiceCatalog'];

    function ServiceCatalogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ServiceCatalog, FacilityEventServiceCatalog) {
        var vm = this;

        vm.serviceCatalog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.facilityeventservicecatalogs = FacilityEventServiceCatalog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.serviceCatalog.id !== null) {
                ServiceCatalog.update(vm.serviceCatalog, onSaveSuccess, onSaveError);
            } else {
                ServiceCatalog.save(vm.serviceCatalog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:serviceCatalogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
