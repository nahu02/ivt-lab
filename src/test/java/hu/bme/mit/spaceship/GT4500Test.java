package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.beans.Transient;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimary, mockSecondary;

  @BeforeEach
  public void init(){
    this.mockPrimary = mock(TorpedoStore.class);
    this.mockSecondary = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimary, mockSecondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty(){
    // Arrange
    when(mockPrimary.isEmpty()).thenReturn(true);
    when(mockSecondary.isEmpty()).thenReturn(false);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondaryEmpty(){
    // Arrange
    when(mockSecondary.isEmpty()).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockPrimary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_BothEmpty(){
    // Arrange
    when(mockSecondary.isEmpty()).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimary, times(0)).fire(1);
    verify(mockSecondary, times(0)).fire(1);

    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimary.isEmpty()).thenReturn(false);
    when(mockSecondary.isEmpty()).thenReturn(false);
    when(mockPrimary.fire(1)).thenReturn(true);
    when(mockSecondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimary, times(1)).fire(1);
    verify(mockSecondary, times(1)).fire(1);
  }

  @Test 
  public void fireTorpedo_All_SecondaryEmpty(){
    // Arrange
    when(mockSecondary.isEmpty()).thenReturn(true);
    when(mockPrimary.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockSecondary, times(0)).fire(1);  
    verify(mockPrimary, times(0)).fire(1);  // even if tihis is not empty we expect it doesn't get 
  }
}
