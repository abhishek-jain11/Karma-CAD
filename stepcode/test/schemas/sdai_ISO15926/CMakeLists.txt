# -----  GENERATED FILE  -----
# -----   Do not edit!   -----

# schema name: lifecycle_integration_schema
# (short name: sdai_ISO15926)
# 201 entities, 0 types

# targets, logic, etc are within a set of macros shared by all schemas
include(${SC_CMAKE_DIR}/SC_CXX_schema_macros.cmake)
set(sdai_ISO15926_file_count 412)

PROJECT(sdai_ISO15926)
# list headers so they can be installed - entity, type, misc
set(sdai_ISO15926_entity_hdrs
     entity/SdaiComposition_of_individual.h                                      entity/SdaiSpecialization_by_domain.h                                       
     entity/SdaiClass_of_cause_of_ending_of_class_of_individual.h                entity/SdaiClass_of_compound.h                                              
     entity/SdaiClass_of_containment_of_individual.h                             entity/SdaiSingle_property_dimension.h                                      
     entity/SdaiClass_of_class.h                                                 entity/SdaiTemporal_whole_part.h                                            
     entity/SdaiOther_relationship.h                                             entity/SdaiActivity.h                                                       
     entity/SdaiClass_of_class_of_information_representation.h                   entity/SdaiDescription.h                                                    
     entity/SdaiNumber_range.h                                                   entity/SdaiClass_of_functional_mapping.h                                    
     entity/SdaiClass_of_indirect_property.h                                     entity/SdaiClass_of_scale.h                                                 
     entity/SdaiCause_of_event.h                                                 entity/SdaiClass_of_individual.h                                            
     entity/SdaiSpecialization.h                                                 entity/SdaiClass_of_particulate_material.h                                  
     entity/SdaiPhysical_object.h                                                entity/SdaiClass_of_event.h                                                 
     entity/SdaiClass_of_feature.h                                               entity/SdaiClass_of_shape_dimension.h                                       
     entity/SdaiBeginning.h                                                      entity/SdaiClass_of_inanimate_physical_object.h                             
     entity/SdaiEnding.h                                                         entity/SdaiBoundary_of_property_space.h                                     
     entity/SdaiRole.h                                                           entity/SdaiShape.h                                                          
     entity/SdaiDocument_definition.h                                            entity/SdaiClass_of_sub_atomic_particle.h                                   
     entity/SdaiClass_of_involvement_by_reference.h                              entity/SdaiDimension_of_individual.h                                        
     entity/SdaiMultidimensional_object.h                                        entity/SdaiScale.h                                                          
     entity/SdaiLower_bound_of_number_range.h                                    entity/SdaiClass_of_definition.h                                            
     entity/SdaiClass_of_individual_used_in_connection.h                         entity/SdaiClass_of_number.h                                                
     entity/SdaiClass_of_assertion.h                                             entity/SdaiClass_of_shape.h                                                 
     entity/SdaiShape_dimension.h                                                entity/SdaiComparison_of_property.h                                         
     entity/SdaiLifecycle_stage.h                                                entity/SdaiPossible_role_and_domain.h                                       
     entity/SdaiStatus.h                                                         entity/SdaiUpper_bound_of_property_range.h                                  
     entity/SdaiClass_of_lifecycle_stage.h                                       entity/SdaiClass_of_relative_location.h                                     
     entity/SdaiClass_of_specialization.h                                        entity/SdaiExpress_logical.h                                                
     entity/SdaiFunctional_physical_object.h                                     entity/SdaiApproval.h                                                       
     entity/SdaiFunctional_mapping.h                                             entity/SdaiMaterialized_physical_object.h                                   
     entity/SdaiClass_of_atom.h                                                  entity/SdaiIntended_role_and_domain.h                                       
     entity/SdaiMultidimensional_property_space.h                                entity/SdaiClass_of_composite_material.h                                    
     entity/SdaiMultidimensional_number.h                                        entity/SdaiParticipation.h                                                  
     entity/SdaiClass_of_class_of_individual.h                                   entity/SdaiClass.h                                                          
     entity/SdaiClass_of_person.h                                                entity/SdaiMultidimensional_number_space.h                                  
     entity/SdaiClass_of_temporal_sequence.h                                     entity/SdaiRole_and_domain.h                                                
     entity/SdaiPeriod_in_time.h                                                 entity/SdaiClass_of_cause_of_beginning_of_class_of_individual.h             
     entity/SdaiSpecialization_of_individual_dimension_from_property.h           entity/SdaiClass_of_connection_of_individual.h                              
     entity/SdaiDirect_connection.h                                              entity/SdaiArranged_individual.h                                            
     entity/SdaiClass_of_multidimensional_object.h                               entity/SdaiClass_of_scale_conversion.h                                      
     entity/SdaiClass_of_organization.h                                          entity/SdaiClass_of_participation.h                                         
     entity/SdaiMultidimensional_property.h                                      entity/SdaiIntersection_of_set_of_class.h                                   
     entity/SdaiClass_of_responsibility_for_representation.h                     entity/SdaiClass_of_class_of_usage_of_representation.h                      
     entity/SdaiClass_of_representation_translation.h                            entity/SdaiCoordinate_system.h                                              
     entity/SdaiExpress_string.h                                                 entity/SdaiClass_of_activity.h                                              
     entity/SdaiClass_of_left_namespace.h                                        entity/SdaiConnection_of_individual.h                                       
     entity/SdaiProperty_range.h                                                 entity/SdaiContainment_of_individual.h                                      
     entity/SdaiTemporal_sequence.h                                              entity/SdaiIdentification.h                                                 
     entity/SdaiPoint_in_time.h                                                  entity/SdaiRight_namespace.h                                                
     entity/SdaiPossible_individual.h                                            entity/SdaiClass_of_usage_of_representation.h                               
     entity/SdaiClass_of_point_in_time.h                                         entity/SdaiNumber_space.h                                                   
     entity/SdaiClass_of_approval.h                                              entity/SdaiClass_of_arrangement_of_individual.h                             
     entity/SdaiClass_of_property_space.h                                        entity/SdaiProperty_for_shape_dimension.h                                   
     entity/SdaiRecognition.h                                                    entity/SdaiBoundary_of_number_space.h                                       
     entity/SdaiClass_of_class_of_description.h                                  entity/SdaiClass_of_composition_of_individual.h                             
     entity/SdaiClass_of_express_information_representation.h                    entity/SdaiClass_of_right_namespace.h                                       
     entity/SdaiClass_of_namespace.h                                             entity/SdaiProperty.h                                                       
     entity/SdaiUpper_bound_of_number_range.h                                    entity/SdaiClass_of_status.h                                                
     entity/SdaiEnumerated_set_of_class.h                                        entity/SdaiCardinality.h                                                    
     entity/SdaiClass_of_possible_role_and_domain.h                              entity/SdaiClass_of_identification.h                                        
     entity/SdaiClass_of_relationship_with_related_end_1.h                       entity/SdaiLower_bound_of_property_range.h                                  
     entity/SdaiRelationship.h                                                   entity/SdaiAbstract_object.h                                                
     entity/SdaiClass_of_relationship_with_related_end_2.h                       entity/SdaiClass_of_class_of_definition.h                                   
     entity/SdaiDimension_of_shape.h                                             entity/SdaiParticipating_role_and_domain.h                                  
     entity/SdaiRepresentation_of_thing.h                                        entity/SdaiProperty_quantification.h                                        
     entity/SdaiThing.h                                                          entity/SdaiClass_of_assembly_of_individual.h                                
     entity/SdaiNamespace.h                                                      entity/SdaiRelative_location.h                                              
     entity/SdaiMultidimensional_scale.h                                         entity/SdaiClass_of_isomorphic_functional_mapping.h                         
     entity/SdaiFeature_whole_part.h                                             entity/SdaiClass_of_arranged_individual.h                                   
     entity/SdaiClass_of_classification.h                                        entity/SdaiClass_of_dimension_for_shape.h                                   
     entity/SdaiStream.h                                                         entity/SdaiInteger_number.h                                                 
     entity/SdaiLeft_namespace.h                                                 entity/SdaiClass_of_temporal_whole_part.h                                   
     entity/SdaiRepresentation_of_gregorian_date_and_utc_time.h                  entity/SdaiClass_of_information_object.h                                    
     entity/SdaiTemporal_bounding.h                                              entity/SdaiClass_of_representation_of_thing.h                               
     entity/SdaiClass_of_feature_whole_part.h                                    entity/SdaiClass_of_information_presentation.h                              
     entity/SdaiClass_of_class_of_representation_translation.h                   entity/SdaiClass_of_relationship.h                                          
     entity/SdaiExpress_binary.h                                                 entity/SdaiIndirect_property.h                                              
     entity/SdaiClass_of_biological_matter.h                                     entity/SdaiClass_of_relationship_with_signature.h                           
     entity/SdaiWhole_life_individual.h                                          entity/SdaiClass_of_direct_connection.h                                     
     entity/SdaiArrangement_of_individual.h                                      entity/SdaiClass_of_functional_object.h                                     
     entity/SdaiClass_of_class_of_relationship.h                                 entity/SdaiActual_individual.h                                              
     entity/SdaiCrystalline_structure.h                                          entity/SdaiEnumerated_number_set.h                                          
     entity/SdaiLanguage.h                                                       entity/SdaiRepresentation_form.h                                            
     entity/SdaiClass_of_class_of_representation.h                               entity/SdaiExpress_real.h                                                   
     entity/SdaiExpress_boolean.h                                                entity/SdaiUnion_of_set_of_class.h                                          
     entity/SdaiIndividual_used_in_connection.h                                  entity/SdaiUsage_of_representation.h                                        
     entity/SdaiClass_of_class_of_composition.h                                  entity/SdaiClass_of_indirect_connection.h                                   
     entity/SdaiClass_of_recognition.h                                           entity/SdaiClass_of_property.h                                              
     entity/SdaiIndirect_connection.h                                            entity/SdaiDifference_of_set_of_class.h                                     
     entity/SdaiClass_of_intended_role_and_domain.h                              entity/SdaiClass_of_information_representation.h                            
     entity/SdaiClass_of_class_of_identification.h                               entity/SdaiClass_of_molecule.h                                              
     entity/SdaiClass_of_period_in_time.h                                        entity/SdaiProperty_space.h                                                 
     entity/SdaiProperty_space_for_class_of_shape_dimension.h                    entity/SdaiClass_of_approval_by_status.h                                    
     entity/SdaiClass_of_organism.h                                              entity/SdaiEnumerated_property_set.h                                        
     entity/SdaiSpecialization_by_role.h                                         entity/SdaiClassification.h                                                 
     entity/SdaiInvolvement_by_reference.h                                       entity/SdaiPhase.h                                                          
     entity/SdaiReal_number.h                                                    entity/SdaiResponsibility_for_representation.h                              
     entity/SdaiDefinition.h                                                     entity/SdaiExpress_integer.h                                                
     entity/SdaiClass_of_abstract_object.h                                       entity/SdaiArithmetic_number.h                                              
     entity/SdaiClass_of_description.h                                           entity/SdaiClass_of_class_of_relationship_with_signature.h                  
     entity/SdaiClass_of_class_of_responsibility_for_representation.h            entity/SdaiSpatial_location.h                                               
     entity/SdaiIndividual_dimension.h                                           entity/SdaiAssembly_of_individual.h                                         
     entity/SdaiEvent.h                                                             )

set(sdai_ISO15926_type_hdrs
        )

set(sdai_ISO15926_misc_hdrs
     Sdaiclasses.h   schema.h
     SdaiLIFECYCLE_INTEGRATION_SCHEMANames.h
     SdaiLIFECYCLE_INTEGRATION_SCHEMA.h
   )

# install all headers
set(all_headers ${sdai_ISO15926_entity_hdrs} ${sdai_ISO15926_type_hdrs} ${sdai_ISO15926_misc_hdrs})
foreach( header_file ${all_headers} )
  set(curr_dir)
  get_filename_component(curr_dir ${header_file} PATH)
  get_filename_component(curr_name ${header_file} NAME)
  if (curr_dir)
    install( FILES ${header_file} DESTINATION "include/schemas/sdai_ISO15926/${curr_dir}" )
  else (curr_dir)
    install( FILES ${header_file} DESTINATION "include/schemas/sdai_ISO15926" )
  endif (curr_dir)
endforeach()
# implementation files - 3 lists

# unity build: #include small .cc files to reduce the number
# of translation units that must be compiled
if(SC_UNITY_BUILD)
  # turns off include statements within type and entity .cc's - the unity T.U.'s include a unity header
  add_definitions( -DSC_SDAI_UNITY_BUILD)
  set(sdai_ISO15926_entity_impls SdaiLIFECYCLE_INTEGRATION_SCHEMA_unity_entities.cc)
  set(sdai_ISO15926_type_impls SdaiLIFECYCLE_INTEGRATION_SCHEMA_unity_types.cc)
else(SC_UNITY_BUILD)
  set(sdai_ISO15926_entity_impls
     entity/SdaiComposition_of_individual.cc                                     entity/SdaiSpecialization_by_domain.cc                                      
     entity/SdaiClass_of_cause_of_ending_of_class_of_individual.cc               entity/SdaiClass_of_compound.cc                                             
     entity/SdaiClass_of_containment_of_individual.cc                            entity/SdaiSingle_property_dimension.cc                                     
     entity/SdaiClass_of_class.cc                                                entity/SdaiTemporal_whole_part.cc                                           
     entity/SdaiOther_relationship.cc                                            entity/SdaiActivity.cc                                                      
     entity/SdaiClass_of_class_of_information_representation.cc                  entity/SdaiDescription.cc                                                   
     entity/SdaiNumber_range.cc                                                  entity/SdaiClass_of_functional_mapping.cc                                   
     entity/SdaiClass_of_indirect_property.cc                                    entity/SdaiClass_of_scale.cc                                                
     entity/SdaiCause_of_event.cc                                                entity/SdaiClass_of_individual.cc                                           
     entity/SdaiSpecialization.cc                                                entity/SdaiClass_of_particulate_material.cc                                 
     entity/SdaiPhysical_object.cc                                               entity/SdaiClass_of_event.cc                                                
     entity/SdaiClass_of_feature.cc                                              entity/SdaiClass_of_shape_dimension.cc                                      
     entity/SdaiBeginning.cc                                                     entity/SdaiClass_of_inanimate_physical_object.cc                            
     entity/SdaiEnding.cc                                                        entity/SdaiBoundary_of_property_space.cc                                    
     entity/SdaiRole.cc                                                          entity/SdaiShape.cc                                                         
     entity/SdaiDocument_definition.cc                                           entity/SdaiClass_of_sub_atomic_particle.cc                                  
     entity/SdaiClass_of_involvement_by_reference.cc                             entity/SdaiDimension_of_individual.cc                                       
     entity/SdaiMultidimensional_object.cc                                       entity/SdaiScale.cc                                                         
     entity/SdaiLower_bound_of_number_range.cc                                   entity/SdaiClass_of_definition.cc                                           
     entity/SdaiClass_of_individual_used_in_connection.cc                        entity/SdaiClass_of_number.cc                                               
     entity/SdaiClass_of_assertion.cc                                            entity/SdaiClass_of_shape.cc                                                
     entity/SdaiShape_dimension.cc                                               entity/SdaiComparison_of_property.cc                                        
     entity/SdaiLifecycle_stage.cc                                               entity/SdaiPossible_role_and_domain.cc                                      
     entity/SdaiStatus.cc                                                        entity/SdaiUpper_bound_of_property_range.cc                                 
     entity/SdaiClass_of_lifecycle_stage.cc                                      entity/SdaiClass_of_relative_location.cc                                    
     entity/SdaiClass_of_specialization.cc                                       entity/SdaiExpress_logical.cc                                               
     entity/SdaiFunctional_physical_object.cc                                    entity/SdaiApproval.cc                                                      
     entity/SdaiFunctional_mapping.cc                                            entity/SdaiMaterialized_physical_object.cc                                  
     entity/SdaiClass_of_atom.cc                                                 entity/SdaiIntended_role_and_domain.cc                                      
     entity/SdaiMultidimensional_property_space.cc                               entity/SdaiClass_of_composite_material.cc                                   
     entity/SdaiMultidimensional_number.cc                                       entity/SdaiParticipation.cc                                                 
     entity/SdaiClass_of_class_of_individual.cc                                  entity/SdaiClass.cc                                                         
     entity/SdaiClass_of_person.cc                                               entity/SdaiMultidimensional_number_space.cc                                 
     entity/SdaiClass_of_temporal_sequence.cc                                    entity/SdaiRole_and_domain.cc                                               
     entity/SdaiPeriod_in_time.cc                                                entity/SdaiClass_of_cause_of_beginning_of_class_of_individual.cc            
     entity/SdaiSpecialization_of_individual_dimension_from_property.cc          entity/SdaiClass_of_connection_of_individual.cc                             
     entity/SdaiDirect_connection.cc                                             entity/SdaiArranged_individual.cc                                           
     entity/SdaiClass_of_multidimensional_object.cc                              entity/SdaiClass_of_scale_conversion.cc                                     
     entity/SdaiClass_of_organization.cc                                         entity/SdaiClass_of_participation.cc                                        
     entity/SdaiMultidimensional_property.cc                                     entity/SdaiIntersection_of_set_of_class.cc                                  
     entity/SdaiClass_of_responsibility_for_representation.cc                    entity/SdaiClass_of_class_of_usage_of_representation.cc                     
     entity/SdaiClass_of_representation_translation.cc                           entity/SdaiCoordinate_system.cc                                             
     entity/SdaiExpress_string.cc                                                entity/SdaiClass_of_activity.cc                                             
     entity/SdaiClass_of_left_namespace.cc                                       entity/SdaiConnection_of_individual.cc                                      
     entity/SdaiProperty_range.cc                                                entity/SdaiContainment_of_individual.cc                                     
     entity/SdaiTemporal_sequence.cc                                             entity/SdaiIdentification.cc                                                
     entity/SdaiPoint_in_time.cc                                                 entity/SdaiRight_namespace.cc                                               
     entity/SdaiPossible_individual.cc                                           entity/SdaiClass_of_usage_of_representation.cc                              
     entity/SdaiClass_of_point_in_time.cc                                        entity/SdaiNumber_space.cc                                                  
     entity/SdaiClass_of_approval.cc                                             entity/SdaiClass_of_arrangement_of_individual.cc                            
     entity/SdaiClass_of_property_space.cc                                       entity/SdaiProperty_for_shape_dimension.cc                                  
     entity/SdaiRecognition.cc                                                   entity/SdaiBoundary_of_number_space.cc                                      
     entity/SdaiClass_of_class_of_description.cc                                 entity/SdaiClass_of_composition_of_individual.cc                            
     entity/SdaiClass_of_express_information_representation.cc                   entity/SdaiClass_of_right_namespace.cc                                      
     entity/SdaiClass_of_namespace.cc                                            entity/SdaiProperty.cc                                                      
     entity/SdaiUpper_bound_of_number_range.cc                                   entity/SdaiClass_of_status.cc                                               
     entity/SdaiEnumerated_set_of_class.cc                                       entity/SdaiCardinality.cc                                                   
     entity/SdaiClass_of_possible_role_and_domain.cc                             entity/SdaiClass_of_identification.cc                                       
     entity/SdaiClass_of_relationship_with_related_end_1.cc                      entity/SdaiLower_bound_of_property_range.cc                                 
     entity/SdaiRelationship.cc                                                  entity/SdaiAbstract_object.cc                                               
     entity/SdaiClass_of_relationship_with_related_end_2.cc                      entity/SdaiClass_of_class_of_definition.cc                                  
     entity/SdaiDimension_of_shape.cc                                            entity/SdaiParticipating_role_and_domain.cc                                 
     entity/SdaiRepresentation_of_thing.cc                                       entity/SdaiProperty_quantification.cc                                       
     entity/SdaiThing.cc                                                         entity/SdaiClass_of_assembly_of_individual.cc                               
     entity/SdaiNamespace.cc                                                     entity/SdaiRelative_location.cc                                             
     entity/SdaiMultidimensional_scale.cc                                        entity/SdaiClass_of_isomorphic_functional_mapping.cc                        
     entity/SdaiFeature_whole_part.cc                                            entity/SdaiClass_of_arranged_individual.cc                                  
     entity/SdaiClass_of_classification.cc                                       entity/SdaiClass_of_dimension_for_shape.cc                                  
     entity/SdaiStream.cc                                                        entity/SdaiInteger_number.cc                                                
     entity/SdaiLeft_namespace.cc                                                entity/SdaiClass_of_temporal_whole_part.cc                                  
     entity/SdaiRepresentation_of_gregorian_date_and_utc_time.cc                 entity/SdaiClass_of_information_object.cc                                   
     entity/SdaiTemporal_bounding.cc                                             entity/SdaiClass_of_representation_of_thing.cc                              
     entity/SdaiClass_of_feature_whole_part.cc                                   entity/SdaiClass_of_information_presentation.cc                             
     entity/SdaiClass_of_class_of_representation_translation.cc                  entity/SdaiClass_of_relationship.cc                                         
     entity/SdaiExpress_binary.cc                                                entity/SdaiIndirect_property.cc                                             
     entity/SdaiClass_of_biological_matter.cc                                    entity/SdaiClass_of_relationship_with_signature.cc                          
     entity/SdaiWhole_life_individual.cc                                         entity/SdaiClass_of_direct_connection.cc                                    
     entity/SdaiArrangement_of_individual.cc                                     entity/SdaiClass_of_functional_object.cc                                    
     entity/SdaiClass_of_class_of_relationship.cc                                entity/SdaiActual_individual.cc                                             
     entity/SdaiCrystalline_structure.cc                                         entity/SdaiEnumerated_number_set.cc                                         
     entity/SdaiLanguage.cc                                                      entity/SdaiRepresentation_form.cc                                           
     entity/SdaiClass_of_class_of_representation.cc                              entity/SdaiExpress_real.cc                                                  
     entity/SdaiExpress_boolean.cc                                               entity/SdaiUnion_of_set_of_class.cc                                         
     entity/SdaiIndividual_used_in_connection.cc                                 entity/SdaiUsage_of_representation.cc                                       
     entity/SdaiClass_of_class_of_composition.cc                                 entity/SdaiClass_of_indirect_connection.cc                                  
     entity/SdaiClass_of_recognition.cc                                          entity/SdaiClass_of_property.cc                                             
     entity/SdaiIndirect_connection.cc                                           entity/SdaiDifference_of_set_of_class.cc                                    
     entity/SdaiClass_of_intended_role_and_domain.cc                             entity/SdaiClass_of_information_representation.cc                           
     entity/SdaiClass_of_class_of_identification.cc                              entity/SdaiClass_of_molecule.cc                                             
     entity/SdaiClass_of_period_in_time.cc                                       entity/SdaiProperty_space.cc                                                
     entity/SdaiProperty_space_for_class_of_shape_dimension.cc                   entity/SdaiClass_of_approval_by_status.cc                                   
     entity/SdaiClass_of_organism.cc                                             entity/SdaiEnumerated_property_set.cc                                       
     entity/SdaiSpecialization_by_role.cc                                        entity/SdaiClassification.cc                                                
     entity/SdaiInvolvement_by_reference.cc                                      entity/SdaiPhase.cc                                                         
     entity/SdaiReal_number.cc                                                   entity/SdaiResponsibility_for_representation.cc                             
     entity/SdaiDefinition.cc                                                    entity/SdaiExpress_integer.cc                                               
     entity/SdaiClass_of_abstract_object.cc                                      entity/SdaiArithmetic_number.cc                                             
     entity/SdaiClass_of_description.cc                                          entity/SdaiClass_of_class_of_relationship_with_signature.cc                 
     entity/SdaiClass_of_class_of_responsibility_for_representation.cc           entity/SdaiSpatial_location.cc                                              
     entity/SdaiIndividual_dimension.cc                                          entity/SdaiAssembly_of_individual.cc                                        
     entity/SdaiEvent.cc                                                            )

  set(sdai_ISO15926_type_impls
        )
endif(SC_UNITY_BUILD)

set( sdai_ISO15926_misc_impls
     SdaiAll.cc    compstructs.cc    schema.cc
     SdaiLIFECYCLE_INTEGRATION_SCHEMA.cc
     SdaiLIFECYCLE_INTEGRATION_SCHEMA.init.cc   )

set(schema_target_files ${sdai_ISO15926_entity_impls} ${sdai_ISO15926_type_impls} ${sdai_ISO15926_misc_impls})
SCHEMA_TARGETS("/Users/AJ/stepcode/data/ISO15926/15926-0002-lifecycle_integration.exp" "lifecycle_integration_schema"
                 "${schema_target_files}")
