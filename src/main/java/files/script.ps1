# Abre los dos archivos de texto
Start-Process notepad.exe "C:\Users\diazj\Desktop\Compiler\src\main\java\files\prueba.txt"
Start-Process notepad.exe "C:\Users\diazj\Desktop\Compiler\src\main\java\files\TablaTokens.txt"
Start-Process notepad.exe "C:\Users\diazj\Desktop\Compiler\src\main\java\files\TablaSimbolos.txt"
Start-Process notepad.exe "C:\Users\diazj\Desktop\Compiler\src\main\java\files\TablaDirecciones.txt"
# Espera un momento para que las ventanas se abran
Start-Sleep -Seconds 1

# Función para mover y redimensionar ventanas
Add-Type @"
    using System;
    using System.Runtime.InteropServices;
    public class Win32 {
        [DllImport("user32.dll", SetLastError = true)]
        public static extern bool SetWindowPos(IntPtr hWnd, IntPtr hWndInsertAfter, int X, int Y, int cx, int cy, uint uFlags);
    }
"@

# Define constantes
$SWP_NOZORDER = 0x0004
$HWND_TOP = [IntPtr]::Zero

# Obtiene las ventanas del Bloc de notas
$window1 = (Get-Process notepad | Select-Object -First 1).MainWindowHandle
$window2 = (Get-Process notepad | Select-Object -Last 1).MainWindowHandle

# Tamaño de la pantalla
$screenWidth = [System.Windows.Forms.Screen]::PrimaryScreen.Bounds.Width
$screenHeight = [System.Windows.Forms.Screen]::PrimaryScreen.Bounds.Height

# Coloca la primera ventana en la mitad izquierda de la pantalla
[Win32]::SetWindowPos($window1, $HWND_TOP, 0, 0, $screenWidth / 2, $screenHeight, $SWP_NOZORDER)

# Coloca la segunda ventana en la mitad derecha de la pantalla
[Win32]::SetWindowPos($window2, $HWND_TOP, $screenWidth / 2, 0, $screenWidth / 2, $screenHeight, $SWP_NOZORDER)