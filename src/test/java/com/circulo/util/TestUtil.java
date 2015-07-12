package com.circulo.util;

import com.circulo.enums.*;
import com.circulo.model.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by tfulton on 6/18/15.
 */
public class TestUtil {

    private static final Logger logger = LoggerFactory.getLogger(TestUtil.class);

    private static final Random random = new Random();

    public static Organization createOrganization() {

        String dateStr = DateUtils.getIso8061(new Date());

        Organization organization = new Organization();
        organization.setId(UUID.randomUUID().toString());
        organization.setName(UUID.randomUUID().toString());
        organization.setDescription(dateStr);

        return organization;
    }

    public static Product createProduct() {
        Product product = new Product();

        product.setId(UUID.randomUUID().toString());
        product.setName("Test Product " + product.getId());
        product.setBrand("Test Brand " + product.getId());
        product.setCategory(createCategory());
        product.setDescription("Test Description " + product.getId());
        product.setStatus(ProductStatus.ACTIVE);
        product.setTags(Arrays.asList("Test Tag1", "Test Tag2", "Test Tag3"));
        product.setVariations(createVariations());

        return product;
    }

    public static List<Variation> createVariations() {
        List<Variation> variations = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Variation variation = new Variation();
            variation.setSku("Test SKU " + UUID.randomUUID().toString());
            variation.setName("Test Variation Name " + variation.getSku());
            variation.setNotes("Test Varation Description" + variation.getSku());
            variations.add(variation);
        }

        return variations;
    }

    public static Category createCategory() {
        Category category = new Category();

        category.setId(UUID.randomUUID().toString());
        category.setName("Test Category " + category.getId());

        return category;
    }

    public static Supplier createSupplier() {
        Supplier supplier = new Supplier();

        supplier.setId(UUID.randomUUID().toString());
        supplier.setName("Test Supplier " + supplier.getId());
        supplier.setAddress(createAddress());

        return supplier;
    }

    public static Address createAddress() {
        Address address = new Address();

        address.setCity("San Francisco");
        address.setCountry("USA");
        address.setState("CA");
        address.setPostalCode("11111");
        address.setPostalAddress1("Test Postal Address");

        return address;
    }

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static String randomString(int len) {
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(random.nextInt(AB.length())));
        return sb.toString();
    }

    public static BigDecimal randomBigDecial(int min, int max) {
        return new BigDecimal(randomInt(min, max)).setScale(2, RoundingMode.CEILING);
    }

    public static Assembly createAssembly() {
        Assembly assembly = new Assembly();
        assembly.setId(UUID.randomUUID().toString());
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC"));
        assembly.setCreatedAt(now);
        assembly.setUpdatedAt(now);
        assembly.setOrganization(createOrganization());
        assembly.setStockLocation(createStockLocation());

        // create assembly input
        List<AssemblyItem> assemblyItemsInput = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AssemblyItem assemblyItem = createAssemblyItem();
            assemblyItem.setCount(i+3);
            assemblyItemsInput.add(assemblyItem);
        }
        assembly.setAssemblyInput(assemblyItemsInput);

        // create assembly output
        List<AssemblyItem> assemblyItemsOutput = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AssemblyItem assemblyItemOutput = (AssemblyItem) GsonSerialUtils.cloneObject(assemblyItemsInput.get(i));
            assemblyItemOutput.setCount(assemblyItemOutput.getCount()-2);
            assemblyItemsOutput.add(assemblyItemOutput);
        }
        assembly.setAssemblyOutput(assemblyItemsOutput);

        // create assembly leftover
        List<AssemblyItem> assemblyItemsLeftOver = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AssemblyItem assemblyItemLeftOver = (AssemblyItem) GsonSerialUtils.cloneObject(assemblyItemsInput.get(i));
            assemblyItemLeftOver.setCount(1);
            assemblyItemsLeftOver.add(assemblyItemLeftOver);
        }
        assembly.setLeftOverAssemblyInput(assemblyItemsLeftOver);

        // create assembly lost
        List<AssemblyItem> assemblyItemsLost = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AssemblyItem assemblyItemLost = (AssemblyItem) GsonSerialUtils.cloneObject(assemblyItemsInput.get(i));
            assemblyItemLost.setCount(1);
            assemblyItemsLost.add(assemblyItemLost);
        }
        assembly.setLostAssemblyInput(assemblyItemsLost);
        
        return assembly;
    }

    public static AssemblyItem createAssemblyItem() {
        AssemblyItem assemblyItem = new AssemblyItem();
        assemblyItem.setSku("Test SKU " + UUID.randomUUID().toString());
        assemblyItem.setCount(0);
        assemblyItem.setUnitOfMeasure("gm");
        assemblyItem.setNotes("Test Notes");
        return assemblyItem;
    }

    private static StockLocation createStockLocation() {
        StockLocation stockLocation = new StockLocation();
        stockLocation.setId(UUID.randomUUID().toString());
        stockLocation.setAddress(createAddress());
        stockLocation.setOrganization(createOrganization());
        stockLocation.setName("Test Stock Location Name");
        stockLocation.setDescription("Test Stock Location Description");
        return stockLocation;
    }

    public static Patient createPatient() {
        Patient patient = new Patient();
        patient.setId(UUID.randomUUID().toString());
        patient.setMember(createMember());
        patient.setRecommendation(createRecommendation());
        patient.setApplicationFormFileId(null);
        patient.setCaregivers(null);
        return patient;
    }

    public static Member createMember() {
        Member member = new Member();
        member.setFirstName("Test First Name");
        member.setMiddleInitial("Test Middle Initial");
        member.setLastName("Test Last Name");
        member.setAddress(createAddress());
        member.setGender(Gender.FEMALE);
        member.setEmail("test@circulo.com");
        member.setHomePhone("123-456-7890");
        member.setMobilePhone("012-345-6789");
        member.setStateID("ABCD1234");
        member.setAlternateID("ABCD1234");
        try {
            member.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1980"));
        } catch (Exception ex) {}


        member.setAlternateIdFileId(null);

        member.setStateIdFileId(null);
        return member;
    }

    public static Caregiver createCaregiver() {
        Caregiver caregiver = new Caregiver();
        caregiver.setId(UUID.randomUUID().toString());
        caregiver.setMember(createMember());
        caregiver.setApplicationFormFileId(null);
        return caregiver;
    }

    public static Recommendation createRecommendation() {
        Recommendation recommendation = new Recommendation();
        recommendation.setDoctor(createDoctor());
        recommendation.setRecommendationFile(null);
        recommendation.setRecommendationNo("ABC123");
        try {
            recommendation.setValidFrom(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1980"));
            recommendation.setValidUpto(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2030"));
        } catch (Exception ex) {}
        recommendation.setVerificationProvider(VerificationProvider.CANNASSIST);
        return recommendation;
    }

    public static Doctor createDoctor() {
        Doctor doctor = new Doctor();
        doctor.setId(UUID.randomUUID().toString());
        doctor.setAddress(createAddress());
        doctor.setCategory("Test Doctor Category");
        doctor.setEmail("doctor@circulo.com");
        doctor.setFirstName("Test Doctor First Name");
        doctor.setLastName("Test Doctor Last Name");
        try {
            doctor.setLicenseExpirationDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2020"));
        } catch (Exception ex) {}
        doctor.setLicenseNo("ABC123");
        doctor.setLicenseStatus(DoctorLicenseStatus.CURRENT);
        doctor.setMobilePhone("123-456-7890");
        doctor.setOfficePhone("012-345-6789");
        return doctor;
    }
}
