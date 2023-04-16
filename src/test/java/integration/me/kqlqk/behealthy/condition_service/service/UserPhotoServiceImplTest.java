package integration.me.kqlqk.behealthy.condition_service.service;

import annotations.ServiceTest;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoAlreadyExistsException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoException;
import me.kqlqk.behealthy.condition_service.exception.exceptions.UserPhotoNotFoundException;
import me.kqlqk.behealthy.condition_service.model.UserPhoto;
import me.kqlqk.behealthy.condition_service.repository.UserPhotoRepository;
import me.kqlqk.behealthy.condition_service.service.impl.UserPhotoServiceImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ServiceTest
public class UserPhotoServiceImplTest {
    @Autowired
    private UserPhotoServiceImpl userPhotoService;

    @Autowired
    private UserPhotoRepository userPhotoRepository;

    public static final String encodedPhoto = "iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAA81" +
            "BMVEX////hHyLeAAALb7YAabPgGx4Aa7QAZ7L409LgHSDxoqPhFBkAbLXwkpXgGBsAZLHxqKnx+PwAYrAAXq/Z6fT+9/c2" +
            "hMD98fHfCA3++vrl8fgAW6386uv5/P77399XiMHpdXazz+Uufb2Docz4y8z52dr0tbbjKSzpYmQAV6vI3e2PstbphIT2vr/l" +
            "NjnmUlTsfoCiw+BwlMVyp9LpbW7kP0HwkJL2xcaCtdrD2et4p9GVudpunctflskAUannVFbkR0nkPT+gsdNBkMeqvdpWnM1Lf7" +
            "yu0OfW6vS9z+SIrNM3isMLebzR2upvlsZkisFHdLaCpaugAAAPgUlEQVR4nO1dCVvqyBIlHUIIwQRCgGZXhk02zQOVVfE5yKjc5f//m" +
            "lfVCZAgejcYQl7ON/eOBNqvD9VddaqS2xUI+PDhw4cPHz58+PDhw4cPH8dG+tgTODg6lWPP4NAozo49g4ODnB17BocGaSSPPYUD41Gp" +
            "HXsKB8aD55fpA+nnjz2Hw+KGzMLHnsNhIdOux0MiUR69zbDieYZtIt94ex/OZNooHnsSh0RHlui1p/OLPpXkyLEncUh06orQ7Rx7FgdEvk" +
            "E52sgcexoHxBkVJNnLshTWqCTPPKxKq1eU44iX/cwVlTiZ8/AubBCJk8jdsadxOCBBjpwfexoHQ3FGOE6iV56VM9UG4WATdqvHnsih0GE" +
            "WlOuerUFFuuBFOUE6e19HLHoiz2gLMoeb8HqbYLLWv/VA/M/3qYIESWPrjfD1I6l5oDpcecQwyAlbcSJ5Vifk2gPRP1mTZSQoc9e2q+n8" +
            "NSH0wQtZVL5NFCRI65GNuZJ3fUJIN+KBHRgInxMBtiBHH237rdOWqUzOPaHeKiwKShy52ZQPq22OKJTGPKFtOo+wBYEhuVqvx3RkRhW44I2" +
            "Caa0OURCd6OZ+YbEBfkcgfU9E+UyEyMBPUug6483UCAQORfFIChxjPgakaGzlRJNnBCK/zHlEm9aQDccp9djqCgQO4ExnHiFYMS0o0LUF09c" +
            "UvCp99ESQgDh4w6Q2t7mfnTxTBFizXY9YMHBNTYKz9ZU7TpHAi3rEyQRiAtgLnApdh4lqHdUp9YLSRmDpHiDQ9ZLM3GJ+IdS9ILURHdxyYLH+" +
            "WpkVZQUjR/+Ys9onYlg25AR54zbPCO5LxSs3LNJtlvLKtju9XZA3ECu8skjzfYp+ZqPWAmmIjuBJPcMQYzvWndZqJhAmEl7xiiBF/Ykpha02Gsa" +
            "NCYLm+pNRJ4UaElK6G0fDGEJ8nHkiawLc1RVYkjaGRYJ+RpLk2yPOap9AV8MJgk2CEkFiIsczj+xFULXZPE1gRnEf4k70RHkGjPgA8Y+2N2wi6HskzO+" +
            "9kltEQGfLs82dtLzM8mHM8L1QJEVg0CebAJ+8RtnGCotXHrhRgSgqCkc3RUTIniwjeudRDCxEkc2mS55RyWQoEK8ERRA2cnezIosP1DIi7XskDU43CEf" +
            "am9c1zlqnimfu5RcbVCC26j2uU7ZSPSPAA5VHmc5srxvEWqaeEeCByo3sYHNjVuA8xDDQqVP78yVh62Zi++MRJ4cOR2y1jAyrwUlS7JMRJ4cOJTZ52mGJ46" +
            "NXAqKJuxubeCvKggef3buTupuyKWRVAjnmbA6C6ux89SPYUCBeKbjZEF4/2N2hyu5QUcjquv5F1+OFf3FevwuYbLMZd1zKWxEj0ydye0fyVGi2SsHL+3t" +
            "xOTH0f2GKv49s88L471/T+eDCyTBgSe0iUdo7qhhx42n0379bU14LqcGS4VozZo3XaTl4uXx9aWY/+EiDRHalv/GmHkfbf5kkorwamhxylr+NuDF55rVLfv" +
            "Ql+7EJbmn487wpbgDFqDbd9+z+HNk5L4rBRHnx6ac62w9f7oCh8TyfGO9pXntCfPh2qcJXX/7BvNI7HoMqxLPOFR2fqDwf5ePvP3o0FJpTTYTvnVeHvzQsD" +
            "uGh+TKez8dONl9CUfi2jL3O8c+gL8F+CLE1/PE3X4gDs+bLy8t4NE0tg4nn8XZw0J/g+wqODjPZ38KXHLMgUlxOWsZi2Nxadxgfs83mcLEwjHFrNJmWyryoaVoid8m33jvd" +
            "+AiWqegmd1owUpplxZAY4pfl51LpP06USqVyuYyfiAZFhKolRD41+KLvMHp8BL4m6CaG6Gnmwfucpoqwg6J8KBR8hxBDMCiqKpoul+oZw+wH+iw7FV3HkGE4mD" +
            "+llqIqWlizgx+Z2dQgX07N54PFD1RZkw/BanBZuFgDvMjwYjwe9Hq9yZRhAj+OxmNjONQ/MtoWLjAg8s1DT/V40MtBCDwuFDV7Q0tFj+Xu/OKP5MhFMMRHE26K" +
            "9++g9/7ESfzDwxpNjFybPoGrf1LfPsqcfgJN2ITRxMhNonQLY01VL37qk4VdZmqWRV4MtdxM8D7Ki0/NH6wxrMgMjZ7xjsgF0NNSP/cNHQno6HmxPH4ZNvUshD8" +
            "71QLmSXpz+PJ3a5LiQ5qmvjodZnzMqyrfcrcXfWLBOqSpy/LbdDJ6bY2NNVqvoLlTZV5LgLwLgo6NJlKGbcsOJ7lEdORqAwKy5UTIlOAhJtxQp6Imh79RlKKUi1p5CF" +
            "wTL9X5ymLZ1vIy2vuJ7OvYyI6DCXVNAnkgO6QZtV/lIbnQxCdDX5V0FuWcOMi6nx8ivpiHcBkGnZQsupBfYHohvvUMfbNJ9XJ0/kv1gaNDH89xx7HFKa7TJ2Bchu05f" +
            "ZdcFAYnKbOz/wwXxhhSehMtSC8g+9+V8frw4eMgGA7+QH2fAIyldpLO8WehP+XE75+Kb1Dep+xS4xPQpqHl2NTezvewkA/SezxdTpqny7G5DDLxLZZT08krKu8LEyi+sZ" +
            "AP0lsNavxo6OI8/lNkv2qmQgutqqZLS4oGLTFuvi0uJ4sTtaP+pjkUKRPeoVB0W6eGctr8RCnGezk1+F52O+mJicTTxamuU+RYjopiiN9BExNFMcin5ouTogcu8gtiE+az" +
            "i9G3ZzSVuRVDQTMBBtZ8+dvXwQlkujZkmxevUz4RfP7Wc5QgCvqFMWY3Cy2UppNRy1j8IE64LQ2ON8dfy2piOXoZ6vH4+8nF47retKBnd3zgHfSem0pRhcWkLCa0p5c9apT" +
            "C21f3WHHxBDtMLe95U4ll14jaeQLv1Zb3+0uzy9xf+/2Nv43CWw69vzrfn9cvxPWBpk3dknjhAxMIbbqXXYiPM47fcrnnsVviZKG0esREDaZe/x7+Pkt2Y/xv9shfaLp5uEY" +
            "/OlNjXfmNBlV1WWJP0vzStOJ684LlG6Uyr+UuL5eti80CXRz/iaG4/ibm1NBKZwZFSBrKz6VvENYHWDds6joGQBvgAsbGhTEe9CbTb99Kz1hShZFaLsG/DXTbMiiMXPHUV0" +
            "E35stcLqFpkBOZmjO4fpQmFOSXy3LKjiUgGDJFnPkZUdQSidxlajDMOm5TLVKXb0ej9Q7Z4bg3T5XZpEWztm0hhGoU/wqt/me+x54cYqXw8tN8sK3AC/ogeJlzEUEL8eyFAW" +
            "tvUiqlykszhUDNraqqCP+xP8DfTBHLqdL062RkXLzzTuBQX95ETeV7RyHxU2CFGPAf4/G41Wq9/rUBvMTKvrG4aDZ3quts82X8xOeA38TtNxF/HVlMQyBciKqWAxHvHl3qROG" +
            "Tx7s/RDzbNF5ZfQrWsaZF9yri943s+GnaM342JBaaQ9i48xKkyUH2NGbunp8bLqZnYjhfihA9xNTbvDcYL5pDDIprQIxkD/XN36y6m6aaj5pq0eVb7/NH4F0EfTieP3//LqoQ" +
            "5+7v73OJNXLw8v7yMpdgxMx7xN+/Pz/1xr8mhdyB+D/gOgaD0QgfwSw5AVdGo9EA75jqbkkj/gAFUGtZJ3YVO3z48OHDh4/TRbHN4MF/mL1CmBBKiWeORNqBMB6PIAk+wxMGMp" +
            "Q8z9D7NvQZnjb+HxgqnmeIh67aGGbS+WInFolEYp3q5rzEvAn7yOTWpXS+ao6rFfPsVIn8+TW8OPoBks5VmimGY406aBwG+aa2Omu2yzCzj4xweMk8aShZrNQaddkaR2exKnLsx" +
            "PqNdw3M/nUwhpzFMBx5oIQqgsTO1JMUSriOechHgyiyLNuP3gv0CVyhFH7KhM8eCBvHTqqTBJk8Yg+hcAUWgisYSiuGt0Q2G3YArJNYrfPKw+TdKXvsiEjzML5bwk7EBOVgfTmc" +
            "LEUygWQynTw6QecqrQEPZbVGzWM85S6zW5odQic/bA7eyTxicw/zzMgIWE/eGld3S6MkB8Mibr5GpFNMJ6u1ByJv7JZkh9Ap3c208xJyNo82veviuDMYl67GHszONMQtHZ8" +
            "dujTfsHcxvKtjGyTZ7NPFTi/nbG2ca6zLFWFGTfbtZ0N36mypk+MvUIbPIn6MNaw0j30usuN16fn6TdagRe7uOvX6lm1a4pJl+lluUblR1p0D2OnlnLJpvMaWLdkZDD" +
            "Js07olq/6MIWO1OrE8hoYRhNVpkOkHbFVGdh/rXWdOyCVH03/GMH1uY3iHnmVzfHmlqyDh3afPdxlDl3QyeafabHAwLLJlKV9ZhwfHOOHjJgluY/ihpzEZEpNh5hZ7da" +
            "2PL79FL0R3HUwHeHQdQ87OMJm/i5zPQHFCjLP3KKnVTf/BwlyabVHH6ZDJdCXSf+jWYRwTRK5iaLNhOnyN4oTKMig3JsVWDLGNAC5MFjHvHpUtAVC5FtbjOPcyzFTOKbZ" +
            "UxTAIYJ0r190f2iw8yqYAwO1L+2t9UGnAOME2zq0Ma11cYQJYkdYBnKOTDnMuMHFYpslbXKQbhVOrU8c4tzFcRwvWjxM0dKMWDler1XDf5mkCgSoKAFPGFHHFKuvmZB1z" +
            "nHxVw2HVIkYSVzFc25DiRGk3bAkVR7QIWPkSh3qzwmE20bA8aXXG9M1sNc590WJlw3PC0p71e9sMI3S1+pjAWWeL5qvNpnQfw5UNWc9YWwRgDLkNwzxuNo5egVvFFjurR" +
            "cqWrMDZTvfG5mWuYmjZsCozb7EJ4ts2DMzQNopgHsour7qUhDHtsGly99qwws4hlzfZwrYNV2lRPkLsyrqCAVCxdaNxMUPWztFWHzx3+FLW0QqnHmMuh6yMXcFfcSI2ZBba" +
            "EKp1FecqDXBsmc5YS72b9a9AGwq2U/ZjsuBOhmwfcgq13sn0FYXbYshaBUmC5Pgmwiz8yQ9WnpE/l12lS+/oxpc+mqUnrpNMp6vXIKClre6AgSqx+uiAMdcXzfSfo4+VdDpd" +
            "BNknuUq1dXA+FsOI2XxEJoRjCYKMFB0M8+aXIDlrFDUzmYBxmFgonKywT7iHIc7YrEZcWSYSFJbPzyJXspNhsm11YFHsPbsgOXaMq0fwi3ALQywCr9vfVq0aKfOppB8OnFNBI" +
            "PZGHR3IIAAKcbSWK14RxTauE2gQGOeSOk2b9ansWvWWfKSOT59gjnAVzmPJUJYdDKtduCLLlHO25S5GBGuc3Kjl2e0B2SU2zMzMIuFqzWXyd2eNh8Z1rMhslC8C7NbKsCuArYJ" +
            "2Jt+5bTxctWNV9ul0tVisuqOBYJI5Cfpw7HkcDmyRuqZ4ewCYfY3sattjqKJXlyS3eL39425mipiZR02YjnSxHspR19zL3DMyZwQzcYl0PUqQZX+SJNOGtzpvOVBsEDLzbpxA" +
            "hM/bHjYgg0d9qA8fPnz48OHDhw8fPnz48Br+B+jnlYCuONWjAAAAAElFTkSuQmCC";

    @AfterEach
    public void close(@Value("${photo.dir}") String dir) throws IOException {
        FileUtils.cleanDirectory(new File(dir));
    }

    @Test
    public void getEncodedPhoto_shouldReturnEncodedPhoto() {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, encodedPhoto);

        String encodedPhoto = userPhotoService.getEncodedPhoto(1, "01-01-23");

        assertThat(encodedPhoto).matches("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");
    }

    @Test
    public void getEncodedPhoto_shouldThrowException() {
        assertThrows(UserPhotoException.class, () -> userPhotoService.getEncodedPhoto(1, "badFormat"));
        assertThrows(UserPhotoNotFoundException.class, () -> userPhotoService.getEncodedPhoto(2, "20-12-23"));
    }

    @Test
    public void save_shouldSaveDateOfPhotoToDbAndPhotoToLocalFiles() {
        int oldUserPhotoSize = userPhotoRepository.findAll().size();

        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, encodedPhoto);

        int newUserPhotoSize = userPhotoRepository.findAll().size();

        assertThat(newUserPhotoSize).isEqualTo(oldUserPhotoSize + 1);
    }

    @Test
    public void savePhoto_shouldThrowException() {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, encodedPhoto);

        UserPhoto userPhotoToSave = userPhotoService.getByUserIdAndDate(1, "01-01-23");
        assertThrows(UserPhotoAlreadyExistsException.class, () -> userPhotoService.save(userPhotoToSave, encodedPhoto));
    }

    @Test
    public void deleteByUserIdAndDate_shouldDeleteUserPhotoAndPath() {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, encodedPhoto);

        userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-22");
        userPhotoService.save(userPhoto, encodedPhoto);

        int oldSize = userPhotoService.getByUserId(1).size();

        userPhotoService.deleteByUserIdAndDate(1, "01-01-22");

        int newSize = userPhotoService.getByUserId(1).size();

        assertThat(newSize).isEqualTo(oldSize - 1);
    }

    @Test
    public void deleteByUserIdAndDate_shouldThrowException() {
        assertThrows(UserPhotoNotFoundException.class, () -> userPhotoService.deleteByUserIdAndDate(1, "01-01-22"));
    }

    @Test
    public void deleteByUser_shouldDeleteUserPhotoAndPath() {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-23");
        userPhotoService.save(userPhoto, encodedPhoto);

        userPhoto = new UserPhoto();
        userPhoto.setUserId(1);
        userPhoto.setPhotoDate(new GregorianCalendar(2022, Calendar.JANUARY, 1).getTime());
        userPhoto.setPhotoPath("src/test/resources/tmp_files/1--01-01-22");
        userPhotoService.save(userPhoto, encodedPhoto);

        userPhotoService.deleteByUserId(1);

        assertThrows(UserPhotoNotFoundException.class, () -> userPhotoService.getByUserId(1));
    }
}


