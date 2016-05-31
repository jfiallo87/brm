'use strict';

describe('Controller Tests', function() {

    describe('ServiceCatalog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockServiceCatalog, MockFacilityEventServiceCatalog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockServiceCatalog = jasmine.createSpy('MockServiceCatalog');
            MockFacilityEventServiceCatalog = jasmine.createSpy('MockFacilityEventServiceCatalog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ServiceCatalog': MockServiceCatalog,
                'FacilityEventServiceCatalog': MockFacilityEventServiceCatalog
            };
            createController = function() {
                $injector.get('$controller')("ServiceCatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:serviceCatalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
