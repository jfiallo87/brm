(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('seating-catalog', {
            parent: 'entity',
            url: '/seating-catalog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SeatingCatalogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seating-catalog/seating-catalogs.html',
                    controller: 'SeatingCatalogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('seating-catalog-detail', {
            parent: 'entity',
            url: '/seating-catalog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SeatingCatalog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/seating-catalog/seating-catalog-detail.html',
                    controller: 'SeatingCatalogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SeatingCatalog', function($stateParams, SeatingCatalog) {
                    return SeatingCatalog.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('seating-catalog.new', {
            parent: 'seating-catalog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seating-catalog/seating-catalog-dialog.html',
                    controller: 'SeatingCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('seating-catalog', null, { reload: true });
                }, function() {
                    $state.go('seating-catalog');
                });
            }]
        })
        .state('seating-catalog.edit', {
            parent: 'seating-catalog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seating-catalog/seating-catalog-dialog.html',
                    controller: 'SeatingCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SeatingCatalog', function(SeatingCatalog) {
                            return SeatingCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seating-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('seating-catalog.delete', {
            parent: 'seating-catalog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/seating-catalog/seating-catalog-delete-dialog.html',
                    controller: 'SeatingCatalogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SeatingCatalog', function(SeatingCatalog) {
                            return SeatingCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('seating-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
