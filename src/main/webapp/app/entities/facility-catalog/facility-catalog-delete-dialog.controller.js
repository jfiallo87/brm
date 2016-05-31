(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityCatalogDeleteController',FacilityCatalogDeleteController);

    FacilityCatalogDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilityCatalog'];

    function FacilityCatalogDeleteController($uibModalInstance, entity, FacilityCatalog) {
        var vm = this;

        vm.facilityCatalog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilityCatalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
