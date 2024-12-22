![abc](https://github.com/user-attachments/assets/bcbc2547-6042-4155-9ef9-ab6cd14cc514)

# Coco Guard

## Purpose 
The purpose of the Coco Guard app is to empower farmers with intelligent tools for identifying coconut tree diseases, forecasting coconut demand, and predicting coconut yield, enabling data-driven decision-making to optimize farming and trade practices. This app is motivated by the current challenges in Sri Lanka, where coconut harvesting is declining due to diseases and rising coconut prices, impacting both local consumption and export opportunities. By providing advanced solutions, Coco Guard aims to help develop the export market, support Sri Lanka's economy, and offer a valuable resource for coconut farmers not only in Sri Lanka but also worldwide.

## Technologies used
- **Firebase: For login, registration, saving yield records, and storing demand forecasting data.**
- **Google Colab: To build and train the three models — yield prediction, demand forecasting prediction, and coconut disease object detection.**
- **Roboflow: For annotating coconut tree disease images.**
- **GCP (Google Cloud Platform): For deploying the three models — yield prediction, demand forecasting prediction, and coconut disease object detection.**
- **Gemini AI: To create coconut tree disease management plans.**

## Demo video Android & Desktop

[![Demo Video](https://github.com/user-attachments/assets/50c201fd-91f3-4ca1-9bc3-0e4157b04c46 )](https://drive.google.com/file/d/1BrAg97vMnwt3mC2tjTFKDJFlfmmvkYRq/view?usp=sharing)


## Features

### 1. Upload coconut tree images and identify diseases
The **Coco Guard** app is designed to help farmers identify diseases affecting coconut trees. Farmers can upload images of their coconut trees, and the app utilizes a trained object detection model (in the computer vision category) to identify diseases. Once a disease is detected, the app provides detailed descriptions of the symptoms and appropriate treatments. Additionally, farmers can create personalized treatment plans using the app's **Gemini AI-powered treatment suggestion feature**, ensuring effective management of diseases.

#### Description:
The screenshot below shows the accuracy levels of the trained model for disease detection in coconut trees. The model's performance is measured by its ability to accurately identify diseases based on uploaded images. Higher accuracy levels indicate the model's effectiveness in detecting various coconut tree diseases.
![Screenshot 2024-12-16 001738](https://github.com/user-attachments/assets/9c4a74da-2c68-4c96-abe9-469f577c613a)

### 2. Coconut Demand Forecasting
The app offers a robust coconut demand forecasting feature. By inputting key parameters such as:
- **Month**
- **Region**
- **Coconut Export Volume (kg)**
- **Domestic Coconut Consumption (kg)**
- **Coconut Prices (Local) (LKR/kg)**
- **International Coconut Prices (USD/kg)**
- **Export Destination Demand (kg)**
- **Currency Exchange Rates (LKR to USD)**
- **Competitor Countries' Production (kg)**

The app predicts the demand for coconuts in a specific region and month. This feature is powered by a trained machine learning model, enabling users to make informed decisions for export and local market strategies.

#### Description:
The accuracy level of the coconut demand forecasting model is displayed in the screenshot below.R² Score: 0.999
This is an excellent result. An R² score close to 1 indicates that the model is explaining almost all of the variance in the data. A score of 0.999 means that the model's predictions are extremely close to the actual values, suggesting very high accuracy.
![Screenshot 2024-11-29 200323](https://github.com/user-attachments/assets/e17cf472-8f49-42aa-9afc-32d02912d9ca)

### 3. Coconut Yield Prediction
Coco Guard also includes a coconut yield prediction feature. By inputting the following parameters:
- **Soil Type**
- **Soil pH**
- **Humidity**
- **Temperature**
- **Sunlight Hours**
- **Month**
- **Plant Age**

The app predicts the coconut yield for the specified conditions. This feature helps farmers optimize their agricultural practices based on accurate data-driven insights.

#### Description:
The screenshot below shows the accuracy level of the trained model for predicting coconut yield. It reflects how effectively the model estimates the yield based on various environmental factors
![Screenshot 2024-12-16 001858](https://github.com/user-attachments/assets/5238d658-45a8-49b0-a106-b0258179e0b5)

### 4. Data Analysis and History Records
Users can save the parameters and predictions for both yield and demand in dedicated history pages:
- **Yield Records**
- **Demand Records**

These records allow farmers to analyze trends and make better decisions for future farming and trade activities.

---
## Screenshots

<img src="https://github.com/user-attachments/assets/023f7c16-d8f1-4fea-ae48-6f2ac5d3a3df" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/0aa48c2f-b640-4765-a2ea-31ef7cfaf49b" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/f0632e2b-3738-4b37-b686-73a401b73c4e" width="250" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/61c4bc7f-5b09-4147-9f20-6b0965f482a2" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/63abda8c-fb9d-4236-88be-4ffa6e694cc9" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/994b5177-9446-482e-b5d2-3b243b3b74ad" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/639acc52-de1b-4a47-946e-8acf473faae3" width="250" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/bdfd0521-529b-41a8-a479-6de55a7a151e" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/bd93f222-1d50-4cb1-9983-3bc4b4aef1fb" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/1383fb63-ba26-4917-895b-144a655f9490" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/4927b98b-e064-474f-b125-3b82c2c092fb" width="250" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/f68e278b-95c1-4eca-8e29-c57e56020662" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/d27bb1f0-31f0-4cc4-b20d-c61b528b4978" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/c23481a1-7045-47f9-836b-4a8f3b543478" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/e4492800-b7b7-4168-94d2-280479a53e42" width="250" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/d1d329c5-6721-4087-94fd-3c1340e77064" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/c57838c9-9b3b-4aca-840d-f01ffccab421" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/675f709a-a844-42eb-bffc-324a2d441ffb" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/a76b116f-c875-454f-9bee-2b4abb440cc3" width="250" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/34e779cd-a842-4754-b756-bd718b014b49" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/062ed276-0791-43bd-b999-b3fee938a29b" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/e1e9b9c9-d09d-41e6-9a71-1dd3c7d3af94" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/797d8f25-d693-4c60-aafe-bb33bdaf5fbd" width="250" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/c8a95f49-a882-465f-b108-121ca6992602" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/d48f81b0-b5a1-487c-b5ea-d818616112c9" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/1b3920c7-43b2-43f4-b5cf-eb51b5c97610" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/349e443d-907d-4279-b065-5e5fc073d45d" width="250" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/c50faa1a-ae51-46a4-b3ba-0b210f6f8ea3" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/f87cebd1-6715-4bb6-8d7b-0de74b8d5e01" width="700" alt="Screenshot 2">
<img src="https://github.com/user-attachments/assets/3d5d1fa8-266c-4aa3-a2a3-97df67b5a090" width="250" alt="Screenshot 2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/user-attachments/assets/035e6b13-ff5b-4c56-93f9-a33918200d20" width="250">
<img src="https://github.com/user-attachments/assets/deab3810-15c7-4775-b8c2-39e93f384249" width="700" alt="Screenshot 2">

## Libraries Used

| **Use**                  | **Source**                              |
|--------------------------|------------------------------------------|
| Navigation               | `org.jetbrains.androidx.navigation:navigation-compose` (2.7.0-alpha07) |
| State Management         | `androidx.lifecycle.viewmodel`, `androidx.lifecycle.runtime.compose` |
| UI Components            | `compose.runtime`, `compose.foundation`, `compose.material`, `compose.ui`, `compose.components.resources`, `compose.components.uiToolingPreview` |
| File Picker              | MohamedRejeb's Calf File Picker (`calf-file-picker` 0.5.3, `calf-file-picker-coil` 0.5.1) |
| Image Loading            | Coil (`io.coil-kt.coil3:coil-compose` 3.0.0-alpha08) |
| HTTP Client/Serialization| Ktor (`ktor-client-core`, `ktor-client-logging`, `ktor-client-content-negotiation`, `ktor-client-serialization`, `ktor-serialization-kotlinx-json`) |
| JSON Handling            | `org.jetbrains.kotlinx:kotlinx-serialization-json` (1.6.0), `org.json:json` (20210307) |
| Firebase Firestore       | GitLive Firebase (`libs.gitlive.firebase.firestore`) |
| Multithreading           | Kotlin Coroutines (`libs.kotlinx.coroutines.swing`) |

## Architecture diagram (MVC)
<img src="https://github.com/user-attachments/assets/4a81d5a2-b4f2-43c3-b4bd-908281371a8f" width="1000" alt="Screenshot 2">

## Set up environment

###  How to Run Your Android Application
You can run your application using an emulator or an Android mobile device. [Learn more](https://developer.android.com/studio/run/managing-avds#createavd)

1. In the list of run configurations, select **Android App** and choose a device.
2. Click the **Run** icon to run the application.
3. If you are using an Android mobile device, I will provide you with the APK file. You can download it onto your mobile phone and use the app.

<img src="https://github.com/user-attachments/assets/21bb6043-e706-4a79-9ebd-c54a0488684f" width="70" height="85" alt="Screenshot 2">

[Download APK](https://drive.google.com/file/d/1DrHVbaz_9AZXDIct93HYp1XGM6u9cNvm/view?usp=sharing)                <img src="https://github.com/user-attachments/assets/af2b58c1-6f38-41cf-b9bc-5c8f7a6e752c" width="100" alt="Screenshot 2">    Install apk to your android mobile   <img src="https://github.com/user-attachments/assets/cf7453e4-23ad-4ab1-8bb4-7c2d2abbaba6" width="200" alt="Screenshot 2">

<img src="https://github.com/user-attachments/assets/21bb6043-e706-4a79-9ebd-c54a0488684f" width="70" height="85" alt="Screenshot 2">

[Download coconut tree disease test images](https://drive.google.com/drive/folders/1bEGF_d7O4XEdt0RVNIk3yYb7x-6uWEtc?usp=sharing) 

## About Me

I am an undergraduate student pursuing a **Computer Science Special Degree** at **NSBM Green University, Sri Lanka**. I specialize in full-stack web and mobile development as well as AI/ML development. During my one-year internship at an IT company, I gained hands-on experience in building applications and developing AI/ML models. My passion lies in creating innovative solutions that blend technology with real-world challenges, such as the Coco Guard app, which aims to empower farmers through intelligent tools.
