(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('ServiceCatalogDeleteController',ServiceCatalogDeleteController);

    ServiceCatalogDeleteController.$inject = ['$uibModalInstance', 'entity', 'ServiceCatalog'];

    function ServiceCatalogDeleteController($uibModalInstance, entity, ServiceCatalog) {
        var vm = this;

        vm.serviceCatalog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ServiceCatalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
