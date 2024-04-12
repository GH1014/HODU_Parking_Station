# HODU_전기차 충전소


**프로젝트 개요**

임베디드 소프트웨어 경진대회 자율공모부분에 제출했던 안드로이드 어플리케이션 부분입니다.

Firebase와 MySQL을 이용하여 데이터베이스와의 연동을 구현하여 

자동차가 충전소에 충전하러 들어오고 나갈 때 이용요금이 정산되어 어플리케이션의 알림으로 동작하도록 구현하였습니다. 

또한, RFID를 이용해 등록된 차량의 정보가 시각적으로 보이도록 했으며, 

알림 및 화면을 통해 사용자의 차량의 배터리 상태와 다른 차량이 충전소에 들어와 있는지 여부를 확인할 수 있습니다.


--------------------------------------------------------

**사용한 기술 정보**

Java, Android Studio, Firebase, MySQL

--------------------------------------------------------

**구동 스크린샷**

![image](https://github.com/GH1014/HODU_Parking_Station/assets/95550744/adbe7517-ce97-464b-8967-c08f97b36947)
![image](https://github.com/GH1014/HODU_Parking_Station/assets/95550744/7f03f9a8-2720-4d2a-a790-312d9947c8f3)
![image](https://github.com/GH1014/HODU_Parking_Station/assets/95550744/d588c263-2fe0-4a70-ba0d-3b8834c240dc)

--------------------------------------------------------

**주요 코드**

Firebase 함수인 onDataChange를 오버라이드 한 함수입니다.

데이터베이스의 값과 비교하여 뷰를 수정하고 알림을 사용자에게 전달합니다.

```java
public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

      //데이터 값 변수에 저장
      Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
      Log.d(TAG, "Value is: " + map);

      StationUsing = "";
      StationUseNum = 0;


      //데이터베이스 값 저장 후 비교 (하드 코딩인 부분이 있어 리팩토링 예정)
      for (int i = 0; i < Stationstr.length; i++ ){
          Map map2 = (Map)map.get("Station" + Stationstr[i]);
          String using = (String)map2.get("USING");
          String carName = (String)map2.get("CAR_NAME");
          int payment = Integer.parseInt(String.valueOf(map2.get("PAYMENT")));

          Car_color = (ImageView)root.findViewById(Station[i]);
          CarNameArr[i] = carName;


          if(using.equals("O") && CAR_NAME.equals(CarNameArr[i])){
              StationUseNum++;

              mStation_arr[i] = true;
              Car_color.setImageResource(R.drawable.carimg03);
          }

          else if(using.equals("O")){
              StationUseNum++;

              mStation_arr[i] = false;
              Car_color.setImageResource(R.drawable.carimg01);
          }

          else if(using.equals("X")){
              if (mStation_arr[i] == true){
                  Toast.makeText(root.getContext(), "주차 요금이 "+ Integer.toString(payment) + " 원 부과되었습니다.", Toast.LENGTH_SHORT).show();
              }

              mStation_arr[i] = false;

              Car_color.setImageResource(R.drawable.carimg02);
          }

          StationUsing = StationUsing + using + " ";
      }

      //USING 값에 따른 텍스트 값 변경
      StationNumText = (TextView) root.findViewById(R.id.useStationNum);
      StationNumText.setText(Integer.toString(StationUseNum));

      Log.d(TAG, "Value is: "+ StationUsing);
  }
}
```

--------------------------------------------------------

안드로이드의 다양한 기능 및 라이브러리에 대한 이해와 활용능력을 향상시키는데 도움이 되었습니다.

Firebase, MySQL 과 같은 데이터베이스에 대한 이해와 연동 능력을 향상시키는데 도움이 되었습니다.
