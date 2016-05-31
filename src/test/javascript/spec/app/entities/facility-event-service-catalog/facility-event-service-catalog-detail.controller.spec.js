'use strict';

describe('Controller Tests', function() {

    describe('FacilityEventServiceCatalog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFacilityEventServiceCatalog, MockServiceCatalog, MockFacilityEventCatalog, MockFacilityEventServiceReservation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFacilityEventServiceCatalog = jasmine.createSpy('MockFacilityEventServiceCatalog');
            MockServiceCatalog = jasmine.createSpy('MockServiceCatalog');
            MockFacilityEventCatalog = jasmine.createSpy('MockFacilityEventCatalog');
            MockFacilityEventServiceReservation = jasmine.createSpy('MockFacilityEventServiceReservation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FacilityEventServiceCatalog': MockFacilityEventServiceCatalog,
                'ServiceCatalog': MockServiceCatalog,
                'FacilityEventCatalog': MockFacilityEventCatalog,
                'FacilityEventServiceReservation': MockFacilityEventServiceReservation
            };
            createController = function() {
                $injector.get('$controller')("FacilityEventServiceCatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:facilityEventServiceCatalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
