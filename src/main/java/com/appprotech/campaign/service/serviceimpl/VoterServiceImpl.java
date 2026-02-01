package com.appprotech.campaign.service.serviceimpl;

import com.appprotech.campaign.common.APIResponse;
import com.appprotech.campaign.common.JwtUtil;
import com.appprotech.campaign.common.UserAccessValidator;
import com.appprotech.campaign.dto.*;
import com.appprotech.campaign.entity.*;
import com.appprotech.campaign.repository.*;
import com.appprotech.campaign.service.VoterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoterServiceImpl implements VoterService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAccessValidator userAccessValidator;

    @Autowired
    private UserBoothAccesseRepository userBoothAccesseRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserServiceImpl  userServiceImpl;

    @Autowired
    private UpdatedVoterRepository updatedVoterRepository;

    @Autowired
    private PartyRepository partyRepository;


    @Override
    @Transactional
    public List<Voter> insertData(VoterBulkRequest request) {

        CommonVoterData common = request.getCommon();
        List<OriginalDataRequest> records = request.getRecords();

        Set<String> incomingEpicIds = records.stream()
                .map(OriginalDataRequest::getVoter_id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<String> existingEpicIds = voterRepository.findExistingEpicIds(incomingEpicIds);

        List<Voter> voters = records.stream()
                .filter(data -> !existingEpicIds.contains(data.getVoter_id()))
                .map(data -> {
                    Voter voter = new Voter();
                    // record-specific fields
                    voter.setEpicId(data.getVoter_id());
                    voter.setExtractId(data.getSl_no());
                    voter.setName(data.getName());
                    voter.setAge(data.getAge());
                    voter.setGender(data.getGender());
                    voter.setHouseNumber(data.getHouse_number());
                    voter.setRelativeName(data.getRelation_name());
                    voter.setRelativeRelation(data.getRelation_type());
                    voter.setCreatedDate(new Date());

                    // common fields
                    voter.setVillageOrMaintown(common.getVillageOrMaintown());
                    voter.setMandal(common.getMandal());
                    voter.setRevenueDivision(common.getRevenueDivision());
                    voter.setDistrict(common.getDistrict());
                    voter.setState(common.getState());
                    voter.setPoliceStation(common.getPoliceStation());
                    voter.setPostOffice(common.getPostOffice());
                    voter.setAssemblyConstituency(common.getAssemblyConstituency());
                    voter.setParliamentary(common.getParliamentary());
                    voter.setBoothNumber(common.getBoothNumber());
                    voter.setPollingStation(common.getPollingStation());
                    voter.setNoAndNameOfSectionsInThePart(common.getNoAndNameOfSectionsInThePart());
                    voter.setAddressOfPollingStation(common.getAddressOfPollingStation());
                    voter.setPincode(common.getPincode()    );
                    return voter;
                })
                .collect(Collectors.toList());
        return voterRepository.saveAll(voters);
    }

        @Override
        public List<VoterResponse> getAllVoters(HttpServletRequest request) {
            long userId = jwtUtil.getUserId(jwtUtil.getToken(request));
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
    //            List<UserBoothAccess> assignedBooths = user.getAssignedBoothNumbers();
            List<UserBoothAccess> assignedBooths=   userServiceImpl.getAssignedBooths(userId);
             if (assignedBooths == null || assignedBooths.isEmpty()) {
                throw new RuntimeException("User does not have any booth access");
            }
            List<Long> boothNumbers = assignedBooths.stream()
                    .map(UserBoothAccess::getBoothNumber)
                    .collect(Collectors.toList());

             List<Voter> fetchedUsers=voterRepository.findAllVoters(boothNumbers);

            List<VoterResponse> responses = Optional
                    .ofNullable(voterRepository.findAllVoters(boothNumbers))
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(this::mapToVoterResponse)
                    .toList();

            return responses;
        }


    //    @Override
    //    public Voter getVoterByEpicId(String epicId,HttpServletRequest request) {
    //        long userId = jwtUtil.getUserId(jwtUtil.getToken(request));
    //        User user = userRepository.findById(userId)
    //                .orElseThrow(() -> new RuntimeException("User not found"));
    //        List<UserBoothAccess> assignedBooths=   userServiceImpl.getAssignedBooths(userId);
    //        if (assignedBooths == null || assignedBooths.isEmpty()) {
    //            throw new RuntimeException("User does not have any booth access");
    //        }
    //        return voterRepository.findByEpicId(epicId)
    //                .orElseThrow(() -> new RuntimeException("Voter not found with EPIC ID: " + epicId));
    //    }


    @Override
    public VoterResponse getVoterByEpicId(String epicId, HttpServletRequest request) {
        Voter voter=findVoter(epicId,request);
        return mapToVoterResponse(voter);
    }

//    @Override
//    public ResponseEntity<APIResponse<VoterUpdateResponse>> updateVoter(VoterUpdateRequest voterUpdateRequest, HttpServletRequest request) {
//        Voter voter=findVoter(voterUpdateRequest.getEpicId(),request);
//        UpdatedVoter updatedVoter=null;
//        VoterUpdateResponse response=null;
//        updatedVoter =updatedVoterRepository.findById(voterUpdateRequest.getUpdateId()).orElseThrow(()->
//                new RuntimeException("Voter not found with EPIC ID:" + voterUpdateRequest.getEpicId()));
//        Voter voterData = voterRepository.findByEpicId(voterUpdateRequest.getEpicId())
//                .orElseThrow(() ->
//                        new RuntimeException("Voter not found with EPIC ID: " + voterUpdateRequest.getEpicId()));
//        if ((voterUpdateRequest.getUpdateId() != null)&(voterUpdateRequest.isUpdateRequired())) {//old record and update required
//            updatedVoter.setName(voterUpdateRequest.getName());
//            updatedVoter.setRelativeName(voterUpdateRequest.getRelativeName());
//            updatedVoter.setRelativeRelation(voterUpdateRequest.getRelativeRelation());
//            updatedVoter.setPhoneNumber(voterUpdateRequest.getPhoneNumber());
//            updatedVoter.setHouseNumber(voterUpdateRequest.getHouseNumber());
//            updatedVoter.setAge(voterUpdateRequest.getAge());
//            updatedVoter.setGender(voterUpdateRequest.getGender());
//            updatedVoter.setVotingPriority(mapToVotingPriority(voterUpdateRequest.getVotingPriorityRequest()));
//            updatedVoterRepository.save(updatedVoter);
//            return toVoterUpdateResponse(updatedVoter);
//        }else if(voterUpdateRequest.getUpdateId() == null&&voterUpdateRequest.isUpdateRequired()){
//            updatedVoter=new UpdatedVoter();
//            updatedVoter.setName(voterUpdateRequest.getName()!=null?voterUpdateRequest.getName():voterData.getName());
//            updatedVoter.setRelativeName(voterUpdateRequest.getRelativeName()!=null?voterUpdateRequest.getRelativeName():voterData.getRelativeName());
//            updatedVoter.setAge(voterUpdateRequest.getAge()!=null?voterUpdateRequest.getAge():voterData.getAge());
//            updatedVoter.setRelativeRelation(voterUpdateRequest.getRelativeRelation());
//            updatedVoter.setPhoneNumber(voterUpdateRequest.getPhoneNumber());
//            updatedVoter.setHouseNumber(voterUpdateRequest.getHouseNumber());
//            updatedVoter.setAge(voterUpdateRequest.getAge());
//            updatedVoter.setGender(voterUpdateRequest.getGender());
//            updatedVoter.setVotingPriority(mapToVotingPriority(voterUpdateRequest.getVotingPriorityRequest()));
//            updatedVoterRepository.save(updatedVoter);
//            return toVoterUpdateResponse(updatedVoter);
//        }else if(voterUpdateRequest.getUpdateId() == null&&!voterUpdateRequest.isUpdateRequired()){
//         updatedVoter=toUpdatedVoter(voterData);
//         updatedVoter.setVotingPriority(mapToVotingPriority(voterUpdateRequest.getVotingPriorityRequest()));
//        updatedVoter=updatedVoterRepository.save(updatedVoter);
//            return toVoterUpdateResponse(updatedVoter);
//
//                    }else{
//            throw new RuntimeException("went wrog");
//
//        }
//    }

    @Override
    @Transactional
    public ResponseEntity<APIResponse<VoterUpdateResponse>> updateVoter(
            VoterUpdateRequest voterUpdateRequest,
            HttpServletRequest request) {

        Long userId=jwtUtil.getUserId(jwtUtil.getToken(request));
     Optional<User>  user=  userRepository.findById(userId);

        // 🔹 Fetch original voter once
        Voter voter = voterRepository.findByEpicId(voterUpdateRequest.getEpicId())
                .orElseThrow(() ->
                        new RuntimeException("Voter not found with EPIC ID: " + voterUpdateRequest.getEpicId()));

        UpdatedVoter updatedVoter;

        // =====================================================
        // CASE 1: Existing update record + update required  (1,1)
        // =====================================================
        if (voterUpdateRequest.getUpdateId() != null && voterUpdateRequest.isUpdateRequired()) {
             updatedVoter = updatedVoterRepository
                    .findByEpicID(voterUpdateRequest.getEpicId())
                    .orElseThrow(() ->
                            new RuntimeException("Update record not found"));

            updatedVoter= applyUpdateFields(updatedVoter, voterUpdateRequest);
            updatedVoter.setUpdatedBy(user.get());
            updatedVoter.setUpdatedDate(LocalDateTime.now());
        }
        // =====================================================
        // CASE 2: No update record + update required****(0,1)
        // =====================================================
        else if (voterUpdateRequest.getUpdateId() == null && voterUpdateRequest.isUpdateRequired()) {
            updatedVoter = toUpdatedVoter(voter);
//            updatedVoter= applyUpdateFields(updatedVoter, voterUpdateRequest);
            updatedVoter.setCreatedBy(user.get());
            updatedVoter.setCreatedDate(LocalDateTime.now());
//            updatedVoter.setVotingPriority(mapToVotingPriority(voterUpdateRequest.getVotingPriorityRequest()));

        }
        // =====================================================
        // CASE 3: No update record + no update required (copy only) ******New Record(0,0)
        // =====================================================
        else if (voterUpdateRequest.getUpdateId() == null && !voterUpdateRequest.isUpdateRequired()) {
            updatedVoter = toUpdatedVoter(voter);
            applyUpdateFields(updatedVoter, voterUpdateRequest);
            updatedVoter.setCreatedBy(user.get());
            updatedVoter.setCreatedDate(LocalDateTime.now());
//            updatedVoter.setVotingPriority(mapToVotingPriority(voterUpdateRequest.getVotingPriorityRequest()));
            }
        // =====================================================
        else {
            throw new RuntimeException("Invalid update request state");
        }

        // 🔹 Voting priority (optional)
//        if (voterUpdateRequest.getVotingPriorityRequest() != null) {
//            updatedVoter.setVotingPriority(
//                    mapToVotingPriority(voterUpdateRequest.getVotingPriorityRequest())
//            );
//        }


        if (voterUpdateRequest.getVotingPriorityRequest() != null) {

            VotingPriority votingPriority =
                    mapToVotingPriority(voterUpdateRequest.getVotingPriorityRequest());

            // ✅ IMPORTANT: set both sides
            votingPriority.setUpdatedVoter(updatedVoter);
            updatedVoter.setVotingPriority(votingPriority);
        }


        UpdatedVoter saved = updatedVoterRepository.save(updatedVoter);

        VoterUpdateResponse response = toVoterUpdateResponse(saved);

        return ResponseEntity.ok(
                APIResponse.success(
                        200,
                        "Voter updated and  fetched successfully",
                        response)
        );
    }



    private UpdatedVoter applyUpdateFields(UpdatedVoter updatedVoter,
                                   VoterUpdateRequest request) {

        if (request.getName() != null)
            updatedVoter.setName(request.getName());

        if (request.getRelativeName() != null)
            updatedVoter.setRelativeName(request.getRelativeName());

        if (request.getRelativeRelation() != null)
            updatedVoter.setRelativeRelation(request.getRelativeRelation());

        if (request.getHouseNumber() != null)
            updatedVoter.setHouseNumber(request.getHouseNumber());

        if (request.getGender() != null)
            updatedVoter.setGender(request.getGender());

        if (request.getAge() != null)
            updatedVoter.setAge(request.getAge());

        if (request.getPhoneNumber() != null)
            updatedVoter.setPhoneNumber(request.getPhoneNumber());
        return updatedVoter;
    }



    private VotingPriority mapToVotingPriority(VotingPriorityRequest votingPriorityRequest){
        VotingPriority votingPriority=new VotingPriority();
        votingPriority.setFirstPriority(getParty(votingPriorityRequest.getFirstPriorityPartyId()));
        votingPriority.setSecondPriority(getParty(votingPriorityRequest.getSecondPriorityPartyId()));
        votingPriority.setThirdPriority(getParty(votingPriorityRequest.getThirdPriorityPartyId()));
        votingPriority.setFourthPriority(getParty(votingPriorityRequest.getFourthPriorityPartyId()));
        votingPriority.setFifthPriority(getParty(votingPriorityRequest.getFifthPriorityPartyId()));
        return votingPriority;
    }

    private Party getParty(Long id) {
        return id == null ? null : partyRepository.getReferenceById(id);
    }

    private Voter findVoter(String epicId, HttpServletRequest request){

        // 1️⃣ Get logged-in userId from JWT
        Long userId = jwtUtil.getUserId(jwtUtil.getToken(request));

        // 2️⃣ Fetch voter first
        Voter voter = voterRepository.findByEpicId(epicId)
                .orElseThrow(() ->
                        new RuntimeException("Voter not found with EPIC ID: " + epicId));

        // 3️⃣ Fetch user's booth access (only numbers)
        List<Long> userBoothNumbers =
                userBoothAccesseRepository.accessBoothNumbeByUserId(userId);

        if (userBoothNumbers == null || userBoothNumbers.isEmpty()) {
            throw new AccessDeniedException("User does not have any booth access");
        }
        // 4️⃣ Check booth authorization
        if (!userBoothNumbers.contains(voter.getBoothNumber())) {
            throw new AccessDeniedException(
                    "No access to booth: " + voter.getBoothNumber()
            );
        }
        // 5️⃣ Authorized
        return voter;
    }


    private VoterResponse mapToVoterResponse(Voter voter){
        VoterResponse response = new VoterResponse();
        response.setId(voter.getId());
        response.setName(voter.getName());
        response.setEpicId(voter.getEpicId());
        response.setRelativeName(voter.getRelativeName());
        response.setRelativeRelation(voter.getRelativeRelation());
        response.setHouseNumber(voter.getHouseNumber());
        response.setGender(voter.getGender());
        response.setVillageOrMaintown(voter.getVillageOrMaintown());
        response.setMandal(voter.getMandal());
        response.setRevenueDivision(voter.getRevenueDivision());
        response.setDistrict(voter.getDistrict());
        response.setState(voter.getState());
        response.setAssemblyConstituency(voter.getAssemblyConstituency());
        response.setParliamentary(voter.getParliamentary());
        response.setBoothNumber(voter.getBoothNumber());
        response.setPollingStation(voter.getPollingStation());
        response.setAddressOfPollingStation(voter.getAddressOfPollingStation());
        return response;

    }

    public VotingPriorityResponse mapToResponse(VotingPriority vp) {
        return VotingPriorityResponse.builder()
                .id(vp.getId())
//                .userId(vp.getUser().getId())
                .firstPriority(mapParty(vp.getFirstPriority()))
                .secondPriority(mapParty(vp.getSecondPriority()))
                .thirdPriority(mapParty(vp.getThirdPriority()))
                .fourthPriority(mapParty(vp.getFourthPriority()))
                .fifthPriority(mapParty(vp.getFifthPriority()))
//                .createdDate(vp.getCreatedDate())
//                .updatedDate(vp.getUpdatedDate())
                .build();
    }

    private PartyResponse mapParty(Party party) {
        if (party == null) return null;

        return PartyResponse.builder()
                .id(party.getId())
                .partyName(party.getPartyName())
                .contestedName(party.getContestedName())
                .partyPresident(party.getPartyPresident())
                .build();
    }

    public UpdatedVoter toUpdatedVoter(Voter voter) {
        if (voter == null) {
            return null;
        }
        UpdatedVoter updated = new UpdatedVoter();
        // Audit reference
        updated.setOriginalId(voter.getId());
        // Common fields
        updated.setExtractId(voter.getExtractId());
        updated.setEpicId(voter.getEpicId());
        updated.setName(voter.getName());
        updated.setRelativeName(voter.getRelativeName());
        updated.setRelativeRelation(voter.getRelativeRelation());
        updated.setHouseNumber(voter.getHouseNumber());
        updated.setGender(voter.getGender());
        updated.setAge(voter.getAge());
        updated.setVillageOrMaintown(voter.getVillageOrMaintown());
        updated.setMandal(voter.getMandal());
        updated.setRevenueDivision(voter.getRevenueDivision());
        updated.setDistrict(voter.getDistrict());
        updated.setState(voter.getState());
        updated.setPoliceStation(voter.getPoliceStation());
        updated.setPostOffice(voter.getPostOffice());
        updated.setAssemblyConstituency(voter.getAssemblyConstituency());
        updated.setParliamentary(voter.getParliamentary());
        updated.setBoothNumber(voter.getBoothNumber());
        updated.setPollingStation(voter.getPollingStation());
        // Metadata
        updated.setNoAndNameOfSectionsInThePart(
                voter.getNoAndNameOfSectionsInThePart()
        );
        updated.setAddressOfPollingStation(
                voter.getAddressOfPollingStation()
        );
        return updated;
    }



    public Voter toVoter(UpdatedVoter updated) {

        if (updated == null) {
            return null;
        }

        Voter voter = new Voter();

        // Restore original ID (important for update)
        voter.setId(updated.getOriginalId());
        // Common fields
        voter.setExtractId(updated.getExtractId());
        voter.setEpicId(updated.getEpicId());
        voter.setName(updated.getName());
        voter.setRelativeName(updated.getRelativeName());
        voter.setRelativeRelation(updated.getRelativeRelation());
        voter.setHouseNumber(updated.getHouseNumber());
        voter.setGender(updated.getGender());
        voter.setAge(updated.getAge());
        voter.setVillageOrMaintown(updated.getVillageOrMaintown());
        voter.setMandal(updated.getMandal());
        voter.setRevenueDivision(updated.getRevenueDivision());
        voter.setDistrict(updated.getDistrict());
        voter.setState(updated.getState());
        voter.setPoliceStation(updated.getPoliceStation());
        voter.setPostOffice(updated.getPostOffice());
        voter.setAssemblyConstituency(updated.getAssemblyConstituency());
        voter.setParliamentary(updated.getParliamentary());
        voter.setBoothNumber(updated.getBoothNumber());
        voter.setPollingStation(updated.getPollingStation());
        voter.setNoAndNameOfSectionsInThePart(
                updated.getNoAndNameOfSectionsInThePart()
        );
        voter.setAddressOfPollingStation(
                updated.getAddressOfPollingStation()
        );

        return voter;
    }


    public VoterUpdateResponse toVoterUpdateResponse(UpdatedVoter updatedVoter) {
        if (updatedVoter == null) {
            return null;
        }
        VoterUpdateResponse response = new VoterUpdateResponse();
        response.setUpdatedId(updatedVoter.getDuplicateId());
        response.setOriginalId(updatedVoter.getOriginalId());
        response.setEpicId(updatedVoter.getEpicId());
        response.setName(updatedVoter.getName());
        response.setRelativeName(updatedVoter.getRelativeName());
        response.setRelativeRelation(updatedVoter.getRelativeRelation());
        response.setHouseNumber(updatedVoter.getHouseNumber());
        response.setGender(updatedVoter.getGender());
        response.setVillageOrMaintown(updatedVoter.getVillageOrMaintown());
        response.setMandal(updatedVoter.getMandal());
        response.setRevenueDivision(updatedVoter.getRevenueDivision());
        response.setDistrict(updatedVoter.getDistrict());
        response.setState(updatedVoter.getState());
        response.setAssemblyConstituency(updatedVoter.getAssemblyConstituency());
        response.setParliamentary(updatedVoter.getParliamentary());
        response.setBoothNumber(updatedVoter.getBoothNumber());
        response.setPollingStation(updatedVoter.getPollingStation());
        response.setVotingPriorityResponse(
                mapToResponse(updatedVoter.getVotingPriority())
        );
        return response;
    }


}
