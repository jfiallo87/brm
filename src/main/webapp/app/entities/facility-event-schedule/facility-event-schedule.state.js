(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-event-schedule', {
            parent: 'entity',
            url: '/facility-event-schedule',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventSchedules'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-schedule/facility-event-schedules.html',
                    controller: 'FacilityEventScheduleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-event-schedule-detail', {
            parent: 'entity',
            url: '/facility-event-schedule/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityEventSchedule'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-event-schedule/facility-event-schedule-detail.html',
                    controller: 'FacilityEventScheduleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilityEventSchedule', function($stateParams, FacilityEventSchedule) {
                    return FacilityEventSchedule.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-event-schedule.new', {
            parent: 'facility-event-schedule',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-schedule/facility-event-schedule-dialog.html',
                    controller: 'FacilityEventScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                start: null,
                                end: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-event-schedule', null, { reload: true });
                }, function() {
                    $state.go('facility-event-schedule');
                });
            }]
        })
        .state('facility-event-schedule.edit', {
            parent: 'facility-event-schedule',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-schedule/facility-event-schedule-dialog.html',
                    controller: 'FacilityEventScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilityEventSchedule', function(FacilityEventSchedule) {
                            return FacilityEventSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-schedule', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-event-schedule.delete', {
            parent: 'facility-event-schedule',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-event-schedule/facility-event-schedule-delete-dialog.html',
                    controller: 'FacilityEventScheduleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilityEventSchedule', function(FacilityEventSchedule) {
                            return FacilityEventSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-event-schedule', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
